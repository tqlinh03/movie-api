package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRate;
import com.tqlinh.movie.modal.payment.Payment;
import com.tqlinh.movie.modal.payment.PaymentMethod;
import com.tqlinh.movie.modal.point.Point;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Exchange extends BaseEntity {

    private Long orderCode;
    @ManyToOne
    @JoinColumn(name = "point_id")
    private Point point;

    @ManyToOne
    @JoinColumn(name = "exchangeRate_id")
    private ExchangeRate exchangeRate;

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "id")
    private Payment payment;
}
