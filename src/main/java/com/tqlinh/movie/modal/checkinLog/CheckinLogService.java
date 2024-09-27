package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.dailyReward.*;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.point.PointRepository;
import com.tqlinh.movie.modal.user.User;
import com.tqlinh.movie.modal.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckinLogService {
    public final CheckinLogRepository checkinLogRepository;
    private final DailyRewardRepository dailyRewardRepository;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    @Transactional
    public Integer checkin(CheckinLogRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        LocalDate currentDate = LocalDate.now();

        DailyReward dailyReward = dailyRewardRepository.findById(request.dailyRewardId())
                .orElseThrow(() -> new EntityNotFoundException("Daily Reward Not Found"));

        CheckinLog checkinLog = checkinLogRepository.findCheckinLogByDateAndUser(currentDate, user.getId());
        if (checkinLog != null) {
            throw new IllegalStateException("Checkin Log Already Exists");
        }
        // plus point
        Point point = pointRepository.findById(user.getPoint().getId())
                .orElseThrow(() -> new EntityNotFoundException("Point Not Found"));
        point.setPoint(point.getPoint() + dailyReward.getPoint());

        // handle streak
        int streak = user.getStreak() + 1;
        System.out.println(streak);
        if(streak > 7) {
            streak = 1;
        }
        user.setStreak(streak);
        userRepository.save(user);

        // save check-in
        CheckinLog newCheckinLog = CheckinLog.builder()
                .date(currentDate)
                .user(user)
                .dailyReward(dailyReward)
                .build();
        return checkinLogRepository.save(newCheckinLog).getId();

    }

    public Integer StreakCheckin(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        return user.getStreak();
    }

}
