package com.tqlinh.movie.modal.dailyReward;
import org.springframework.stereotype.Service;

@Service
public class DailyRewardMapper {
    public DailyReward toDailyReWard(DailyRewardRequest request) {
        return DailyReward.builder()
                .day(request.day())
                .point(request.point())
                .build();
    }

    public DailyRewardResponse toResponse(DailyReward dailyReward) {
        return DailyRewardResponse.builder()
                .day(dailyReward.getDay())
                .point(dailyReward.getPoint())
                .build();
    }

    public DailyReward setDailyReward(DailyReward dailyReward, DailyRewardRequest request) {
        dailyReward.setDay(request.day());
        dailyReward.setPoint(request.point());
        return dailyReward;
    }

}
