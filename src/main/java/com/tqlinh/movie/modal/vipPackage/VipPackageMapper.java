package com.tqlinh.movie.modal.vipPackage;

import org.springframework.stereotype.Service;

@Service
public class VipPackageMapper {
    public VipPackage toVipPackage(VipPackageRequest request) {
        return VipPackage.builder()
                .name(request.name())
                .numberOfMonths(request.numberOfMonths())
                .point(request.point())
                .build();
    }

    public VipPackage setExchangeRate(VipPackage vipPackage, VipPackageRequest request) {
        vipPackage.setName(request.name());
        vipPackage.setNumberOfMonths(request.numberOfMonths());
        vipPackage.setPoint(request.point());
        return vipPackage;
    }

    public VipPackageResponse toVipPackageResponse(VipPackage vipPackage) {
        return VipPackageResponse.builder()
                .name(vipPackage.getName())
                .monthDuration(vipPackage.getNumberOfMonths())
                .point(vipPackage.getPoint())
                .build();
    }
}
