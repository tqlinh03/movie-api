package com.tqlinh.movie.modal.exchange;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {
    public final ExchangeService exchangeService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid ExchangeRequest exchangeRequest,
            Authentication connectedUser,
            HttpServletRequest request
    ) throws UnsupportedEncodingException {
        return ResponseEntity.ok(exchangeService.create(exchangeRequest, connectedUser, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeResponse> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(exchangeService.findById(id));
    }
}
