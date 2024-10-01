package com.tqlinh.movie.modal.auth;

import com.tqlinh.movie.modal.user.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.io.IOException;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request,
            HttpServletResponse response
    ) throws MessagingException {
        return ResponseEntity.ok(service.authenticate(request, response));
    }
    @GetMapping("/account")
    public ResponseEntity<UserResponse> fetchAccount(
            @RequestParam String accessToken) throws MessagingException{
        return ResponseEntity.ok(service.fetchAccount(accessToken));
    }

    @PostMapping("/sendValidationEmail")
    public ResponseEntity<String> sendValidationEmail(
            @RequestParam String email) throws MessagingException {
        service.sendCodeEmail(email);
        return ResponseEntity.accepted().body("Đã gửi mã kích hoạt tài khoản.");
    }

    @GetMapping("/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        service.activateAccount(token);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        return ResponseEntity.ok(service.refreshToken(request, response));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletResponse response) {
        service.logout(response);
        return ResponseEntity.accepted().body("Ok.");
    }

}
