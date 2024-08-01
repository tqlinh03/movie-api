package com.tqlinh.movie.modal.payment;

public record PaymentQueryRequest (
        String vnp_TxnRef,
        String vnp_TransactionDate
) {

}
