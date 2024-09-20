package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.dailyReward.*;
import com.tqlinh.movie.modal.point.Point;
import com.tqlinh.movie.modal.point.PointRepository;
import com.tqlinh.movie.modal.user.User;
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
        List<LocalDate> checkinDates = checkinLogRepository.findCheckinDateByUserId(user.getId());
        Integer streak = 1;
        LocalDate previousDate = checkinDates.getFirst();

        for (int i = 1; i < checkinDates.size(); i++) {
            LocalDate currentDate = checkinDates.get(i);

            if (currentDate.equals(previousDate.minusDays(1))) {
                streak++;
            } else {
                break;
            }
            previousDate = currentDate;
        }
    return streak;
    }

}
