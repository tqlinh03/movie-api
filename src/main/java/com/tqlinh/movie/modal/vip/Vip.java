package com.tqlinh.movie.modal.vip;


import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.vipPackage.VipPackage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Vip {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDateTime vipStartDate;
    private LocalDateTime vipEndDate;

    @ManyToOne
    @JoinColumn(name = "vipPackage_Id", referencedColumnName = "id")
    private VipPackage vipPackage;

}
