package com.tqlinh.movie.modal.dailyReward;

import com.tqlinh.movie.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class DailyReward extends BaseEntity {
    private long point;
    private Integer day;

    @OneToMany(mappedBy = "dailyReward")
    private List<DailyReward> dailyRewards;
}
