package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.exchangeRate.ExchangeRate;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRateRepository;
import com.tqlinh.movie.modal.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExchangeMapper {
    public final ExchangeRateRepository exchangeRateRepository;

    public Exchange toExchange(ExchangeRequest request) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(request.exchangeRateId())
                .orElseThrow(() -> new RuntimeException("Not found exchange rate"));
        return Exchange.builder()
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .exchangeRate(exchangeRate)
                .build();
    }

    public ExchangeResponse toResponse(Exchange exchange) {
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .id(exchange.getPayment().getId())
                .amount(exchange.getPayment().getAmount())
                .status(exchange.getPayment().getStatus())
                .paymentMethod(exchange.getPayment().getPaymentMethod())
                .paymentUrl(exchange.getPayment().getPaymentUrl())
                .build();
        return ExchangeResponse.builder()
                .id(exchange.getId())
                .paymentResponse(paymentResponse)
                .build();
    }
}
