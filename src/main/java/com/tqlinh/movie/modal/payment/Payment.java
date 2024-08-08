package com.tqlinh.movie.modal.payment;

import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.notification.Notification;
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
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment extends BaseEntity {
    private BigDecimal amount;
    private Boolean status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToOne
    @JoinColumn(name = "notification_id")
    private Notification notification;

}
