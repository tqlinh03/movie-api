package com.tqlinh.movie.modal.notification;

import com.tqlinh.movie.modal.payment.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue
    private Integer id;
    private String orderReference;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;

}
