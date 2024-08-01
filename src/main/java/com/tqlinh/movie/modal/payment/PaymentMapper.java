package com.tqlinh.movie.modal.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .status(false)
                .build();
    }
}
