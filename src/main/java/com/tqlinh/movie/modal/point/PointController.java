package com.tqlinh.movie.modal.point;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/points")
public class PointController {
    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/point-of-user")
    public ResponseEntity<PointResponse> findPointOfUser(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(pointService.findPointOfUser(connectedUser));
    }
}
