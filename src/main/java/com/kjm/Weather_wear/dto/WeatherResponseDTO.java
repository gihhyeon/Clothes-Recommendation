package com.kjm.Weather_wear.dto;

import com.kjm.Weather_wear.entity.Weather;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherResponseDTO {

    private Weather weather;
    private String message;
}
