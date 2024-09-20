package com.tqlinh.movie.modal.dailyReward;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DailyRewardRepository extends JpaRepository<DailyReward, Integer> {
    DailyReward findByday(Integer day);

    @Query("""
        SELECT d
        FROM DailyReward d
        ORDER BY d.day ASC 
""")
    List<DailyReward> findAllDailyReward();
}
