package com.tqlinh.movie.modal.view;

import com.tqlinh.movie.modal.user.UserMapper;
import com.tqlinh.movie.modal.user.UserRepository;
import com.tqlinh.movie.modal.user.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ViewMapper {
    public final UserMapper userMapper;

    public ViewResponse toResponse(View view){
        List<UserResponse> users = view.getUsers().stream()
                .map(userMapper::toResponse)
                .toList();
        return ViewResponse.builder()
                .id(view.getId())
                .users(users)
                .build();
    }
}
