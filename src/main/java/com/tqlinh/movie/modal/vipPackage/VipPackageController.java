package com.tqlinh.movie.modal.vipPackage;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.exchangeRate.ExchangeRateResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vip-package")
@RequiredArgsConstructor
public class VipPackageController {
    public final VipPackageService vipPackageService;

    @PostMapping
    public ResponseEntity<Integer> create(
            @RequestBody @Valid VipPackageRequest request
    ) {
        return ResponseEntity.ok((vipPackageService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Integer> update(
            @RequestBody @Valid VipPackageRequest request,
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(vipPackageService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Integer id
    ) {
        vipPackageService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VipPackageResponse> findById(
            @PathVariable("id") Integer id
    ) {
        return ResponseEntity.ok(vipPackageService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<VipPackageResponse>> findAll(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page,
        @RequestParam(name = "size", defaultValue = "0", required = false) int size

    ) {
        return ResponseEntity.ok(vipPackageService.findAll(page, size));
    }
}
