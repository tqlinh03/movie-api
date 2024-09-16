package com.tqlinh.movie.modal.checkinLog;

import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.dailyReWard.DailyReward;
import com.tqlinh.movie.modal.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CheckinLog extends BaseEntity {
    private LocalDate checkinDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dailyReward_id")
    private DailyReward dailyReward;
}
