package com.tqlinh.movie.modal.exchangeRate;

import com.tqlinh.movie.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateController {
    public final ExchangeRateService exchangeRateService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid ExchangeRateRequest request
    ) {
        return ResponseEntity.ok((exchangeRateService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> update(
            @RequestBody @Valid ExchangeRateRequest request,
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(exchangeRateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        exchangeRateService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(exchangeRateService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ExchangeRateResponse>> findAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(exchangeRateService.findAll(page, size));
    }
}
