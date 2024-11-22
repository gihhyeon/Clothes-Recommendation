package com.kjm.Weather_wear.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "weather_table") // 테이블 이름 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region; // Region과의 연관 관계

    // 생성자: region을 제외한 모든 필드를 초기화
    public Weather(Double temp, Double minTemp, Double maxTemp, Double rainAmount, Double humid,
                   Double windSpeed, Double rainProbability, String rainType, String skyCondition, String lastUpdateTime) {
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.rainAmount = rainAmount;
        this.humid = humid;
        this.windSpeed = windSpeed;
        this.rainProbability = rainProbability;
        this.rainType = rainType;
        this.skyCondition = skyCondition;
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}