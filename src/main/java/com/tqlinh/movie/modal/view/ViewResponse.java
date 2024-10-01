package com.tqlinh.movie.modal.view;

import com.tqlinh.movie.modal.user.UserResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ViewResponse {
    private int id;
    private List<UserResponse> users;
}
