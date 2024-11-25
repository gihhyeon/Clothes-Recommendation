package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.WeatherResponseDTO;
import com.kjm.Weather_wear.service.ClothingRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/clothing")
@RequiredArgsConstructor
public class ClothingController {

    private final WeatherApiController weatherApiController;
    private final ClothingRecommendationService clothingRecommendationService;

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getClothingRecommendations(
            @RequestParam int nx,
            @RequestParam int ny,
            @RequestParam String userType) {

        log.info("Received parameters: nx={}, ny={}, userType={}", nx, ny, userType);

        try {
            // 1. WeatherApiController를 호출하여 날씨 정보 가져오기
            ResponseEntity<List<WeatherResponseDTO>> weatherResponse = weatherApiController.getRegionWeather(nx, ny);

            if (weatherResponse.getBody() == null || weatherResponse.getBody().isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(List.of(Map.of("message", "날씨 정보를 가져올 수 없습니다.")));
            }

            List<WeatherResponseDTO> weatherList = weatherResponse.getBody();

            // 2. 지역 이름 추출
            String regionName = weatherList.get(0).getRegionName();

            // 3. 1시간 단위 예보 기온 리스트 생성
            List<Double> temperatures = weatherList.stream()
                    .map(weather -> weather.getWeather().getTemp())
                    .filter(Objects::nonNull) // null 값 필터링
                    .toList();

            if (temperatures.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body(List.of(Map.of("message", "예보 데이터가 없습니다.")));
            }

            // 4. ClothingRecommendationService를 사용해 옷 추천 생성
            Map<String, List<String>> clothingRecommendations =
                    clothingRecommendationService.getClothingRecommendations(temperatures, userType);

            // 5. 응답 데이터 생성 (List 형태)
            List<Map<String, Object>> responseList = new ArrayList<>();

            // 지역 정보 추가
            Map<String, Object> regionInfo = new LinkedHashMap<>();
            regionInfo.put("regionName", regionName);
            regionInfo.put("weather", weatherList.stream()
                    .map(WeatherResponseDTO::getWeather) // Weather 객체만 전달
                    .toList());
            responseList.add(regionInfo);

            // 옷 추천 정보 추가
            clothingRecommendations.forEach((temp, recommendations) -> {
                Map<String, Object> recommendationEntry = new LinkedHashMap<>();
                recommendationEntry.put("temperature", temp);
                recommendationEntry.put("recommendations", recommendations);
                responseList.add(recommendationEntry);
            });

            return ResponseEntity.ok(responseList);

        } catch (Exception e) {
            log.error("Clothing recommendation 에러: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(List.of(Map.of("message", "추천 데이터를 가져오는 중 문제가 발생했습니다.")));
        }
    }
}