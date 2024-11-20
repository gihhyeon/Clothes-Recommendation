package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.entity.Weather;
import com.kjm.Weather_wear.repository.ClothingRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClothingRecommendationService {

    private final ClothingRepository clothingRepository;

    public ClothingRecommendationService(ClothingRepository clothingRepository) {
        this.clothingRepository = clothingRepository;
    }

    public List<String> recommendClothing(Weather weather, String userType) {
        double temperature = weather.getTemp();
        int adjustedTemperature = adjustTemperatureForUserType((int) temperature, userType);

        return recommendBasedOnTemperature(adjustedTemperature);
    }

    private int adjustTemperatureForUserType(int temperature, String userType) {
        switch (userType) {
            case "더위를 잘 타는 사람":
                return temperature - 3;
            case "추위를 잘 타는 사람":
                return temperature + 3;
            default: // 평균
                return temperature;
        }
    }

    private List<String> recommendBasedOnTemperature(int temperature) {
        List<String> recommendations = new ArrayList<>();

        if (temperature >= 27) {
            recommendations.add("반팔");
            recommendations.add("반바지");
            recommendations.add("나시");
        } else if (temperature >= 23) {
            recommendations.add("얇은 셔츠");
            recommendations.add("바람막이");
        } else if (temperature >= 20) {
            recommendations.add("롱슬리브");
            recommendations.add("슬랙스");
            recommendations.add("얇은 자켓");
        } else if (temperature >= 17) {
            recommendations.add("니트");
            recommendations.add("맨투맨");
            recommendations.add("후드집업");
            recommendations.add("얇은 자켓");
            recommendations.add("청바지");
        } else if (temperature >= 12) {
            recommendations.add("블루종");
            recommendations.add("슬랙스");
            recommendations.add("가죽 자켓");
        } else if (temperature >= 9) {
            recommendations.add("트렌치코트");
            recommendations.add("경량 패딩");
        } else if (temperature >= 5) {
            recommendations.add("무스탕");
            recommendations.add("코트");
        } else {
            recommendations.add("코트");
            recommendations.add("패딩");
            recommendations.add("긴 바지");
        }

        return recommendations;
    }
}