package com.tqlinh.movie.modal.vip;

import com.tqlinh.movie.common.BaseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vip")
@RequiredArgsConstructor
public class VipController extends BaseEntity {
    public final VipService vipService;

    @PostMapping("/purchaseVip")
    public ResponseEntity<Integer> purchaseVip(
            @RequestBody @Valid VipRequest request,
            Authentication authentication

    ) {
        return ResponseEntity.ok(vipService.purchaseVip(request, authentication));
    }

}
