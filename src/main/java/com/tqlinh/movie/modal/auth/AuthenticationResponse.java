package com.tqlinh.movie.modal.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String email;
    private String lastName;
    private String firstName;
}
