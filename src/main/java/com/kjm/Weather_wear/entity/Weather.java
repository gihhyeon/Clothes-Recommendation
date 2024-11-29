package com.kjm.Weather_wear.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "weather_table") // 테이블 이름 지정
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String forecastDate;      // 예보일자 (YYYYMMDD)
    private String forecastTime;      // 예보시간 (HHmm)
    private Double temp; // 1시간 기온 (TMP)
    private Double minTemp; // 일 최저기온 (TMN)
    private Double maxTemp; // 일 최고기온 (TMX)
    private Double rainAmount; // 1시간 강수량 (PCP)
    private Double humid; // 습도 (REH)
    private Double windSpeed; // 풍속 (WSD)
    private Double rainProbability; // 강수확률 (POP)
    private String rainType; // 강수형태 (PTY)
    private String skyCondition; // 하늘상태 (SKY)

    private String lastUpdateTime; // 최신 갱신 시각


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @JsonIgnore
    private Region region; // Region과의 연관 관계


    public void setRegion(Region region) {
        this.region = region;
    }
}