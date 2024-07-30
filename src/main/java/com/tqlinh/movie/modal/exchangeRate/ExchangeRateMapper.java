package com.tqlinh.movie.modal.exchangeRate;

import org.springframework.stereotype.Service;

@Service
public class ExchangeRateMapper {
    public ExchangeRate toExchangeRate(ExchangeRateRequest exchangeRateRequest) {
        return ExchangeRate.builder()
                .currency(exchangeRateRequest.currency())
                .point(exchangeRateRequest.point())
                .build();
    }

    public ExchangeRateResponse toExchangeRateResponse(ExchangeRate exchangeRate) {
        return ExchangeRateResponse.builder()
                .currency(exchangeRate.getCurrency())
                .point(exchangeRate.getPoint())
                .build();
    }

    public ExchangeRate setExchangeRate(ExchangeRate exchangeRate, ExchangeRateRequest exchangeRateRequest) {
        exchangeRate.setCurrency(exchangeRateRequest.currency());
        exchangeRate.setPoint(exchangeRateRequest.point());
        return exchangeRate;
    }

}
