package com.tqlinh.movie.modal.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqlinh.movie.modal.email.EmailService;
import com.tqlinh.movie.modal.email.EmailTemplateName;
import com.tqlinh.movie.modal.episodeAccess.EpisodeAccess;
import com.tqlinh.movie.modal.episodeAccess.EpisodeAccessRepository;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.point.PointRepository;
import com.tqlinh.movie.modal.token.Token;
import com.tqlinh.movie.modal.token.TokenRepository;
import com.tqlinh.movie.modal.user.User;
import com.tqlinh.movie.modal.user.UserMapper;
import com.tqlinh.movie.modal.user.UserRepository;
import com.tqlinh.movie.modal.user.UserResponse;
import com.tqlinh.movie.modal.vip.Vip;
import com.tqlinh.movie.modal.vip.VipRepository;
import com.tqlinh.movie.modal.vipPackage.VipName;
import com.tqlinh.movie.modal.vipPackage.VipPackage;
import com.tqlinh.movie.modal.vipPackage.VipPackageRepository;
import com.tqlinh.movie.modal.watchlist.Watchlist;
import com.tqlinh.movie.modal.watchlist.WatchlistRepository;
import com.tqlinh.movie.security.JwtService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
//
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final PointRepository pointRepository;
    private final VipRepository vipRepository;
    private final WatchlistRepository watchlistRepository;
    private final EpisodeAccessRepository episodeAccessRepository;
    private final VipPackageRepository vipPackageRepository;
    private final UserMapper userMapper;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        Vip vip = new Vip();
        Point point = new Point();

        Watchlist watchlist = new Watchlist();
        EpisodeAccess episodeAccess = new EpisodeAccess();
        var user = User.builder()
                .firstName(request.getFirstname())
                .lastName(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .streak(0)
                .point(pointRepository.save(point))
                .vip(vipRepository.save(vip))
                .watchlist(watchlistRepository.save(watchlist))
                .episodeAccess(episodeAccessRepository.save(episodeAccess))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    void sendCodeEmail(String email) throws MessagingException {
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng."));
        sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request,
                                               HttpServletResponse response) throws MessagingException {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());

        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        var refreshToken = jwtService.generateRefreshToken(user);
        setCookie(response, refreshToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    private void setCookie(HttpServletResponse response, String refreshToken) {
        var cookie = new Cookie("refresh_token", refreshToken);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
    }

    public UserResponse fetchAccount(String accessToken) {
        var userName = jwtService.extractUsername(accessToken)  ;
        var userResponse = userRepository.findByEmail(userName)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UsernameNotFoundException("Không tìm thấy người dùng."));
        return userResponse;
    }

    public String refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String refreshToken = "";
        final String userEmail;
        Cookie[] cookies = request.getCookies();


        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "refresh_token")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        Boolean isTokenExpired = jwtService.isTokenExpired(refreshToken);
        if(!isTokenExpired) {
            userEmail = jwtService.extractUsername(refreshToken);
            if (userEmail != null) {
                var user = this.userRepository.findByEmail(userEmail)
                        .orElseThrow();
                if (jwtService.isTokenValid(refreshToken, user)) {
                    var accessToken = jwtService.generateToken(user);
                    return accessToken;
                }
            }
        }

        return null;
    }
}
