package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.dailyReward.*;
import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckinLogService {
    public final CheckinLogRepository checkinLogRepository;
    private final DailyRewardRepository dailyRewardRepository;

    public Integer checkin(CheckinLogRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        LocalDate currentDate = LocalDate.now();

        DailyReward dailyReward = dailyRewardRepository.findById(request.dailyRewardId())
                .orElseThrow(() -> new EntityNotFoundException("Daily Reward Not Found"));

        CheckinLog checkinLog = checkinLogRepository.findCheckinLogByDateAndUser(currentDate, user.getId());
        if (checkinLog != null) {
            return null;
        }

        CheckinLog newCheckinLog = CheckinLog.builder()
                .date(currentDate)
                .user(user)
                .dailyReward(dailyReward)
                .build();
        return checkinLogRepository.save(newCheckinLog).getId();

    }

}
