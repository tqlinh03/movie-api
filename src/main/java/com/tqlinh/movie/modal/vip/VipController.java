package com.tqlinh.movie.modal.vip;

import com.tqlinh.movie.common.BaseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

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

    @GetMapping("vip-user-info")
    public ResponseEntity<VipResponse> getInfoVipUser(
            Authentication connected
    ) {
        return ResponseEntity.ok(vipService.getInfoVipUser(connected));
    }
}
