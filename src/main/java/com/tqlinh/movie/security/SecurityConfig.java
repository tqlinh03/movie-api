package com.tqlinh.movie.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.tqlinh.movie.modal.user.Permisstion.*;
import static com.tqlinh.movie.modal.user.Role.ADMIN;
import static com.tqlinh.movie.modal.user.Role.MANAGER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final String[] WHITE_LIST_URL = {
            "/exchange/**",
            "/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers(WHITE_LIST_URL)
                                .permitAll()
//                        PERMISSION MANAGER
                                .requestMatchers("/api/v1/management").hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(GET, "/api/v1/management").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(GET, "/api/v1/management/:id").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, "/api/v1/management").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())

//                       EXCHANGER RATE
                                .requestMatchers("/api/v1/exchange-rate").hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(POST, "/api/v1/exchange-rate").hasAnyAuthority((ADMIN_CREATE.name()), MANAGER_CREATE.name())
                                .requestMatchers(PATCH, "/api/v1/exchange-rate").hasAnyAuthority((ADMIN_UPDATE.name()), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/exchange-rate").hasAnyAuthority((ADMIN_DELETE.name()), MANAGER_DELETE.name())
                                .requestMatchers(GET, "/api/v1/exchange-rate").hasAnyAuthority((ADMIN_READ.name()), MANAGER_READ.name())
                                .anyRequest()
                                .authenticated()
                )

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }
}
