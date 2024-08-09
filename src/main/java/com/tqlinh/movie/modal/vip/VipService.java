package com.tqlinh.movie.modal.vip;

import com.tqlinh.movie.modal.point.PointService;
import com.tqlinh.movie.modal.user.User;
import com.tqlinh.movie.modal.vipPackage.VipPackage;
import com.tqlinh.movie.modal.vipPackage.VipPackageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VipService {
    public final VipRepository vipRepository;
    public final VipPackageRepository vipPackageRepository;
    public final PointService pointService;

    @Transactional
    public Integer purchaseVip(VipRequest request,  Authentication authentication) {
        VipPackage vipPackage = vipPackageRepository.findById(request.vipPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Vip Not Found"));

        User user = (User) authentication.getPrincipal();

        Vip vip = user.getVip();
        LocalDateTime vipStartDate = LocalDateTime.now();
        LocalDateTime vipEndDate = LocalDateTime.now().plusMonths(vipPackage.getNumberOfMonths());
        vip.setVipStartDate(vipStartDate);
        vip.setVipEndDate(vipEndDate);
        vip.setVipPackage(vipPackage);

        pointService.deductPoints(vipPackage.getPoint(), user);

        return vipRepository.save(vip).getId();
    }
}
