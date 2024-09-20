package com.tqlinh.movie.modal.vipPackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface VipPackageRepository extends JpaRepository<VipPackage, Integer> {
    VipPackage findByName(VipName vipName);

    @Query("""
        SELECT v
        FROM VipPackage v
        WHERE v.name = 'VIP'
        ORDER BY v.numberOfMonths ASC
""")
    List<VipPackage> findAllSuperVip();
}
