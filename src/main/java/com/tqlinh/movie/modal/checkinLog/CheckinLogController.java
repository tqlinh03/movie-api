package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.dailyReward.DailyRewardRequest;
import com.tqlinh.movie.modal.dailyReward.DailyRewardResponse;
import com.tqlinh.movie.modal.dailyReward.DailyRewardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/check-in")
@RequiredArgsConstructor
public class CheckinLogController {
    public final CheckinLogService checkinLogService;

    @PostMapping
    public ResponseEntity<Integer> checkin(
            @RequestBody @Valid CheckinLogRequest request,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok((checkinLogService.checkin(request, connectedUser)));
    }

    @GetMapping("streak")
    public ResponseEntity<Integer> StreakCheckin(
            Authentication connectedUser
    ) {
        return ResponseEntity.ok((checkinLogService.StreakCheckin(connectedUser)));
    }





}
