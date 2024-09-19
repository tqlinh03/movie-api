package com.tqlinh.movie.modal.dailyReward;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DailyRewardResponse {
    private long point;
    private Integer day;
}