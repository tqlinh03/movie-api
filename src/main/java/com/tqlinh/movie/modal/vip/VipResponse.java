package com.tqlinh.movie.modal.vip;

import com.tqlinh.movie.modal.vipPackage.VipName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class VipResponse {
    private Integer id;
    private LocalDateTime vipStartDate;
    private LocalDateTime vipEndDate;
    private VipName vipPackageName;
    private String remainTime;
}
