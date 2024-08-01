package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.payment.PaymentResponse;
import lombok.Builder;

@Builder
public class ExchangeResponse {
    private Integer id;
    private PaymentResponse paymentResponse;
}
