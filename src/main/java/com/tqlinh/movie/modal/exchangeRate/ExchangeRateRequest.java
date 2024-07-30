package com.tqlinh.movie.modal.exchangeRate;


import jakarta.validation.constraints.NotNull;

public record ExchangeRateRequest (
        @NotNull(message = "200")
        Integer currency,
        @NotNull(message = "201")
        Integer point
) {
}
