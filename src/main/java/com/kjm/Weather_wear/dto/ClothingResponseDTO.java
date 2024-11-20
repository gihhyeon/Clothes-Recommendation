package com.kjm.Weather_wear.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClothingResponseDTO {
    private List<String> recommendedClothing; // 추천된 옷 리스트
    private int adjustedTemperature;        // 조정된 기온
}