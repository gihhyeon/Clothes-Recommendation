package com.kjm.Weather_wear.dto;

import com.kjm.Weather_wear.entity.Clothing;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClothingResponseDTO {
    private int adjustedTemperature;        // 조정된 기온
    private List<Clothing> recommendedClothing; // 추천된 옷 리스트
}