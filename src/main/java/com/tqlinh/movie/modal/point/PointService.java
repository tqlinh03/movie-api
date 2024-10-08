package com.tqlinh.movie.modal.point;

import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    public final PointRepository pointRepository;
    public final PointMapper pointMapper;

    public void deductPoints(Integer pointAmount, User user) {
        Point point = pointRepository.findById(user.getPoint().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not found point"));
        if (point.getPoint() >= pointAmount) {
            point.setPoint(point.getPoint() - pointAmount);
            pointRepository.save(point);
        } else {
            throw new RuntimeException("Không đủ điểm");
        }

    }

    public PointResponse findPointOfUser(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Point point = pointRepository.findById(user.getPoint().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not found point"));
        return pointMapper.toResponse(point);
    }
}
