package com.tqlinh.movie.modal.exchangeRate;

import lombok.*;

@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class ExchangeRateResponse {
    private Integer id;
    private Integer currency;
    private Integer point;
}
