package com.tqlinh.movie.modal.point;

import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
    public final PointRepository pointRepository;

    public void deductPoints(Integer pointAmount, User user) {
        Point point = pointRepository.findById(user.getPoint().getId())
                .orElseThrow(() -> new EntityNotFoundException("Not found point"));
        if (user.getPoint().getPoint() >= pointAmount) {
            point.setPoint(point.getPoint() - pointAmount);
            pointRepository.save(point);
        } else {
            throw new RuntimeException("Không đủ điểm");
        }

    }
}
