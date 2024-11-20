package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.dto.ClothingResponseDTO;
import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothingRecommendationService {

    private final ClothingRepository clothingRepository;

    public ClothingResponseDTO recommendClothing(double temp, String userType) {
        // 1. 사용자 민감도를 반영한 기온 조정
        int adjustedTemperature = adjustTemperatureForUserType((int) temp, userType);

        // 2. DB에서 추천 기온에 맞는 옷 조회
        List<Clothing> recommendedClothing = clothingRepository.findByTemperatureRange(adjustedTemperature);

        // 3. DTO로 변환하여 반환
        return new ClothingResponseDTO(adjustedTemperature, recommendedClothing);
    }

    private int adjustTemperatureForUserType(int temp, String userType) {
        switch (userType) {
            case "더위를 잘 타는 사람":
                return temp - 3;
            case "추위를 잘 타는 사람":
                return temp + 3;
            default: // 평균
                return temp;
        }
    }
}