package com.tqlinh.movie.modal.user;

import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
}
