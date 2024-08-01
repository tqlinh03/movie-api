package com.tqlinh.movie.modal.payment;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod
) {
}
