package com.tqlinh.movie.modal.payment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest (
        BigDecimal amount,
        PaymentMethod paymentMethod
) {
}
