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

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class VipService {
    public final VipRepository vipRepository;
    public final VipMapper vipMapper;
    public final VipPackageRepository vipPackageRepository;
    public final PointService pointService;

    @Transactional
    public Integer purchaseVip(VipRequest request,  Authentication authentication) {
        VipPackage vipPackage = vipPackageRepository.findById(request.vipPackageId())
                .orElseThrow(() -> new EntityNotFoundException("Vip Not Found"));

        User user = (User) authentication.getPrincipal();

        Vip vip = user.getVip();
        if (vip.getVipPackage() == null) {
            LocalDateTime vipStartDate = LocalDateTime.now();
            LocalDateTime vipEndDate = LocalDateTime.now().plusMonths(vipPackage.getNumberOfMonths());
            vip.setVipStartDate(vipStartDate);
            vip.setVipEndDate(vipEndDate);
            vip.setVipPackage(vipPackage);

            pointService.deductPoints(vipPackage.getPoint(), user);
        } else {
            LocalDateTime vipEndDate = vip.getVipEndDate().plusMonths(vipPackage.getNumberOfMonths());
            vip.setVipEndDate(vipEndDate);
            pointService.deductPoints(vipPackage.getPoint(), user);
        }

        return vipRepository.save(vip).getId();
    }

    public VipResponse getInfoVipUser(Authentication connected) {
        User user = (User) connected.getPrincipal();
        LocalDateTime now = LocalDateTime.now();
        VipResponse response = vipMapper.toResponse(user.getVip());
        if (user.getVip().getVipEndDate().isAfter(now)) {
            String remainTime = getRemainingVipTime(user.getVip());
            response.setRemainTime(remainTime);
        }
        return response;
    }

    public String getRemainingVipTime(Vip vip) {
        LocalDateTime now = LocalDateTime.now();

        Period period = Period.between(now.toLocalDate(), vip.getVipEndDate().toLocalDate());
        Duration duration = Duration.between(now, vip.getVipEndDate());

        long days = duration.toDays() % 30;
        long months = period.getMonths();
        long years = period.getYears();

        StringBuilder remainingTime = new StringBuilder();
        if (years > 0) {
            remainingTime.append(years).append(" năm ");
        }
        if (months > 0) {
            remainingTime.append(months).append(" tháng ");
        }
        remainingTime.append(days).append(" ngày");

        return remainingTime.toString().trim();
    }
}
