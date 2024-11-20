package com.kjm.Weather_wear.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Weather {

    private Double temp; // 1시간 기온 (TMP)
    private Double minTemp; // 일 최저기온 (TMN)
    private Double maxTemp; // 일 최고기온 (TMX)
    private Double rainAmount; // 1시간 강수량 (PCP)
    private Double humid; // 습도 (REH)
    private Double windSpeed; // 풍속 (WSD)
    private Double rainProbability; // 강수확률 (POP)
    private String rainType; // 강수형태 (PTY)
    private String skyCondition; // 하늘상태 (SKY)

    private String lastUpdateTime; // 마지막 갱신 시각 (시간 단위)
}