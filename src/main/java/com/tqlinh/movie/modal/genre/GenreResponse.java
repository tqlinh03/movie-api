package com.tqlinh.movie.modal.genre;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GenreResponse {
    private Integer id;
    private String name;
}
