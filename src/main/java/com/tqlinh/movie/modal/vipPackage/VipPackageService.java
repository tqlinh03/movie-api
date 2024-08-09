package com.tqlinh.movie.modal.vipPackage;

import com.tqlinh.movie.common.PageResponse;
import com.tqlinh.movie.modal.vipPackage.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VipPackageService {
    public final VipPackageMapper vipPackageMapper;
    public final VipPackageRepository vipPackageRepository;


    public Integer create(VipPackageRequest request) {
        VipPackage vipPackage = vipPackageMapper.toVipPackage(request);
        return vipPackageRepository.save(vipPackage).getId();
    }

    public Integer update(Integer id, VipPackageRequest request) {
        VipPackage vipPackage = vipPackageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Vip Package"));
        return vipPackageRepository.save(vipPackageMapper.setExchangeRate(vipPackage, request)).getId();
    }

    public void delete(Integer id) {
        VipPackage vipPackage = vipPackageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found Vip Package"));
         vipPackageRepository.delete(vipPackage);
    }

    public VipPackageResponse findById(Integer id) {
        VipPackage vipPackage = vipPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found Vip Package"));
        return vipPackageMapper.toVipPackageResponse(vipPackage);

    }

    public PageResponse<VipPackageResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<VipPackage> vipPackages = vipPackageRepository.findAll(pageable);
        List<VipPackageResponse> responses = vipPackages.stream()
                .map(vipPackageMapper::toVipPackageResponse)
                .toList();
        return new PageResponse<>(
                responses,
                vipPackages.getNumber(),
                vipPackages.getSize(),
                vipPackages.getTotalElements(),
                vipPackages.getTotalPages(),
                vipPackages.isFirst(),
                vipPackages.isLast()
        );
    }

//    public List<VipPackageResponse> findAll() {
//        List<VipPackageResponse> exchangeRateResponsese = vipPackageRepository.findAll()
//                .stream()
//                .map(vipPackageMapper::toExchangeRateResponse)
//                .toList();
//        return exchangeRateResponsese;
//
//    }
}
