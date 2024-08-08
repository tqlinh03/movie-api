package com.tqlinh.movie.modal.exchange;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {
    public final ExchangeService exchangeService;

    @PostMapping("/create-payment-link")
    public ResponseEntity<String> createPaymentLink(
            @RequestBody @Valid CreatePaymentLinkRequest  request,
            Authentication connectedUser
    ) throws Exception {
        return ResponseEntity.ok(exchangeService.createPaymentLink(request, connectedUser));
    }

    @PostMapping("/confirm-webhook")
    public ObjectNode confirmWebhook(
            @RequestBody Map<String, Object> requestBody
    ) {
        return exchangeService.confirmWebhook(requestBody);
    }
}
