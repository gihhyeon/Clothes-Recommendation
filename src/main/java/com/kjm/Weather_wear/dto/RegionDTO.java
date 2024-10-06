package com.kjm.Weather_wear.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegionDTO {

    private Long id; // 지역 순번
    private String parentRegion; // 시, 도
    private String childRegion; // 시, 군, 구
    private String grandChildRegion; // 동, 읍, 면, 리
    private int nx; // x좌표
    private int ny; // y좌표

}