package com.tqlinh.movie.modal.exchangeRate;

import com.tqlinh.movie.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExchangeRate extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private Integer currency;
    private Integer point;
}
