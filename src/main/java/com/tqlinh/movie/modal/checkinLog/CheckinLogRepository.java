package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.modal.dailyReward.DailyReward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface CheckinLogRepository extends JpaRepository<CheckinLog, Integer> {
    @Query("""
        SELECT checkinLog
        FROM CheckinLog checkinLog
        WHERE checkinLog.date = :currentDate
        AND checkinLog.user.id = :userId
    """)
    CheckinLog findCheckinLogByDateAndUser(LocalDate currentDate, Integer userId);

    @Query("""
    SELECT c.date
    FROM CheckinLog c
    WHERE c.user.id = :userId
    ORDER BY c.date DESC
""")
    List<LocalDate> findCheckinDateByUserId(Integer userId);
}
