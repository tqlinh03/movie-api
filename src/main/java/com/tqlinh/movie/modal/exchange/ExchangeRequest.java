package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.payment.PaymentMethod;

import java.math.BigDecimal;

public record ExchangeRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer exchangeRateId
) {
}
