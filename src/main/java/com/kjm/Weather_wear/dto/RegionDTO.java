package com.kjm.Weather_wear.dto;

import com.kjm.Weather_wear.entity.Weather;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegionDTO {

    private Long id;
    private String parentRegion;
    private String childRegion;
    private String grandChildRegion;
    private int nx;
    private int ny;
    private Weather weather;

    public RegionDTO(Long id, String parentRegion, String childRegion, String grandChildRegion, int nx, int ny, Weather weather) {
        this.id = id;
        this.parentRegion = parentRegion;
        this.childRegion = childRegion;
        this.grandChildRegion = grandChildRegion;
        this.nx = nx;
        this.ny = ny;
        this.weather = weather;
    }
}