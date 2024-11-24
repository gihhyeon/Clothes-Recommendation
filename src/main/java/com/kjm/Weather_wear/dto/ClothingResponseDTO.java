package com.kjm.Weather_wear.dto;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.entity.Weather;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClothingResponseDTO {

    private String regionName; // 지역 이름
    private Weather weather; // 날씨 정보
    private List<Clothing> recommendedClothing; // 추천 옷 리스트
}