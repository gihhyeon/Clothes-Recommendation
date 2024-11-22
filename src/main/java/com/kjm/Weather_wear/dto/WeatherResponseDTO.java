package com.kjm.Weather_wear.dto;

import com.kjm.Weather_wear.entity.Weather;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WeatherResponseDTO {

    private Weather weather;
    private String message;
    private String regionName;

    // 추가 필드: 예보 데이터
    private String forecastDate;      // 예보일자 (YYYYMMDD)
    private String forecastTime;      // 예보시간 (HHmm)
    private Double temp;              // 기온
    private Double minTemp;           // 최저기온
    private Double maxTemp;           // 최고기온
    private Double rainAmount;        // 강수량
    private Double humid;             // 습도
    private Double windSpeed;         // 풍속
    private Double rainProbability;   // 강수확률
    private String rainType;          // 강수형태
    private String skyCondition;      // 하늘상태
    private String lastUpdateTime;    // 마지막 갱신 시각
}
