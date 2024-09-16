package com.tqlinh.movie.modal.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserResponse {
    private String email;
    private String firstName;
    private String lastName;
}
