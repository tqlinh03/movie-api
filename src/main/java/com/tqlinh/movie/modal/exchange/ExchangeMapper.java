package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.exchangeRate.ExchangeRate;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRateRepository;
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
                .exchangeRate(exchangeRate)
                .payment(request.payment())
                .point(request.point())
                .exchangeRate(exchangeRate)
                .orderCode(request.orderCode())
                .build();
    }
}
