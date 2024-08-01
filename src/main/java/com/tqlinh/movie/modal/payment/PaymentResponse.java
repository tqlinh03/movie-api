package com.tqlinh.movie.modal.payment;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class PaymentResponse {
    private Integer id;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private Boolean status;
    private String paymentUrl;
}
