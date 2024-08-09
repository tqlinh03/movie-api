package com.tqlinh.movie.modal.vip;


import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.vipPackage.VipPackage;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Vip extends BaseEntity {
    private LocalDateTime vipStartDate;
    private LocalDateTime vipEndDate;

    @ManyToOne
    @JoinColumn(name = "vipPackage_Id", referencedColumnName = "id")
    private VipPackage vipPackage;

}
