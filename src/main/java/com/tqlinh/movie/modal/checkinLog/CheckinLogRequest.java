package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.modal.dailyReward.DailyReward;
import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public record CheckinLogRequest(
       Integer dailyRewardId
) {

}
