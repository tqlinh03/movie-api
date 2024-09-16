package com.tqlinh.movie.modal.point;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Builder
public class PointResponse {
    private int id;
    private long point;

}
