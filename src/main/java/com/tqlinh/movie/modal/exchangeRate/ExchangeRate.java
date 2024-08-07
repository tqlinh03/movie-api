package com.tqlinh.movie.modal.exchangeRate;

import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.exchange.Exchange;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExchangeRate extends BaseEntity {

    private Integer currency;
    private Integer point;

    @OneToMany(mappedBy = "exchangeRate")
    private List<Exchange> exchanges;
}
