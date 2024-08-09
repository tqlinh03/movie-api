package com.tqlinh.movie.modal.vipPackage;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VipPackageResponse {
    private String name;
    private Integer monthDuration;
    private Integer point;
}
