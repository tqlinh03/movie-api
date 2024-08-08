package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.payment.Payment;
import com.tqlinh.movie.modal.point.Point;
import lombok.Builder;

@Builder
public record ExchangeRequest(
        Integer exchangeRateId,
        Payment payment,
        Point point,
        Long orderCode
        ) {
}
