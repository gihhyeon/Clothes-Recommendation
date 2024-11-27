package com.kjm.Weather_wear.dto;

import com.kjm.Weather_wear.entity.Weather;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class WeatherResponseDTO {

    private String regionName;
    private String message;
    private Weather weather;

}
