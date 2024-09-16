package com.tqlinh.movie.modal.point;

import org.springframework.stereotype.Service;

@Service
public class PointMapper {
    public PointResponse toResponse(Point point) {
        return PointResponse.builder()
                .id(point.getId())
                .point(point.getPoint())
                .build();
    }
}
