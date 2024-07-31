package com.tqlinh.movie.modal.point;

import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.exchange.Exchange;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point")
@EntityListeners(AuditingEntityListener.class)
public class Point extends BaseEntity {
    private long point;

    @OneToMany(mappedBy = "point")
    List<Exchange> exchanges;
}
