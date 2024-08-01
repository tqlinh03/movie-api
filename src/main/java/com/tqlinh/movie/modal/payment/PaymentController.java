package com.tqlinh.movie.modal.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    public final PaymentService paymentService;

    @PostMapping("/processPayment")
    public ResponseEntity<Integer> processPayment(
            @RequestBody @Valid ProcessPaymentRequest request
        ) {
            Integer response = paymentService.processPayment(request);
            return ResponseEntity.ok(response);

    }
    @PostMapping("/payment-query")
    public ResponseEntity<String> paymentQuery (
            @RequestBody @Valid PaymentQueryRequest paymentQueryRequest,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(paymentService.paymentQuery(paymentQueryRequest,request));
    }
}