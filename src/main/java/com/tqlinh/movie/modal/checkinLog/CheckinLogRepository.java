package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.modal.dailyReward.DailyReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface CheckinLogRepository extends JpaRepository<CheckinLog, Integer> {
    @Query("""
        SELECT checkinLog
        FROM CheckinLog checkinLog
        WHERE checkinLog.date = :currentDate
        AND checkinLog.id = :userId
    """)
    CheckinLog findCheckinLogByDateAndUser(LocalDate currentDate, Integer userId);
}
