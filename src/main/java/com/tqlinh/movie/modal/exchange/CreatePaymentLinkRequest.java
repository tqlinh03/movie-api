package com.tqlinh.movie.modal.exchange;

import com.tqlinh.movie.modal.payment.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class CreatePaymentLinkRequest {
//    private String productName;
//    private String description;
    private String returnUrl;
    private String cancelUrl;
    private BigDecimal price;
    private PaymentMethod paymentMethod;
    private Integer exchangeRate_id;
    private Long orderCode;

}
