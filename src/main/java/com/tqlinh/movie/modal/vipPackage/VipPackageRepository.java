package com.tqlinh.movie.modal.vipPackage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VipPackageRepository extends JpaRepository<VipPackage, Integer> {
    VipPackage findByName(VipName vipName);
}
