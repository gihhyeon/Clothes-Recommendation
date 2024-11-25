package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.entity.Clothing;
import com.kjm.Weather_wear.repository.ClothingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ClothingRecommendationService {

    private final ClothingRepository clothingRepository;

    /**
     * 기온과 사용자 타입에 따라 추천 의상을 반환합니다.
     *
     * @param temperatures 1시간 단위의 예보 기온 리스트
     * @param userType 사용자의 타입 (추위를 잘 타는 사람, 평균, 더위를 잘 타는 사람)
     * @return 추천 의상 리스트
     */
    public Map<String, List<String>> getClothingRecommendations(List<Double> temperatures, String userType) {
        Map<String, List<String>> clothingRecommendations = new LinkedHashMap<>();

        for (Double temperature : temperatures) {
            double adjustedTemp = getUserTypeAdjustment(temperature, userType);

            // 온도 범위에 따라 중복된 아이템도 포함하도록 처리
            List<String> recommendedClothes = clothingRepository.findAllByTemperatureRange(adjustedTemp)
                    .stream()
                    .map(clothing -> String.format("%s - %s", clothing.getCategory(), clothing.getItemName()))
                    .distinct() // 중복 아이템 제거
                    .toList();

            clothingRecommendations.put(String.format("%.1f°C", adjustedTemp), recommendedClothes);
        }

        return clothingRecommendations;
    }

    private double getUserTypeAdjustment(Double temp, String userType) {
        switch (userType) {
            case "coldSensitive":
                return temp - 3.0; // 추위를 잘 타는 사람
            case "hotSensitive":
                return temp + 3.0; // 더위를 잘 타는 사람
            default:
                return temp; // 평균
        }
    }
}