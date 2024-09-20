package com.tqlinh.movie.modal.vip;

import org.springframework.stereotype.Service;

@Service
public class VipMapper {
    public VipResponse toResponse(Vip vip) {
        return VipResponse.builder()
                .id(vip.getId())
                .vipStartDate(vip.getVipStartDate())
                .vipEndDate(vip.getVipEndDate())
                .vipPackageName(vip.getVipPackage().getName())
                .build();
    }
}
