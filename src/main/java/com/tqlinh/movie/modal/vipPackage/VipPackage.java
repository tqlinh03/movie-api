package com.tqlinh.movie.modal.vipPackage;

import com.tqlinh.movie.common.BaseEntity;
import com.tqlinh.movie.modal.vip.Vip;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class VipPackage extends BaseEntity {
    private String name;
    private Integer numberOfMonths;
    private Integer point;

    @OneToMany(mappedBy = "vipPackage")
    private List<Vip> vips;
}
