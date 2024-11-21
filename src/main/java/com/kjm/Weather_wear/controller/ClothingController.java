package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.ClothingResponseDTO;
import com.kjm.Weather_wear.service.ClothingRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clothing")
@RequiredArgsConstructor
public class ClothingController {

    private final ClothingRecommendationService recommendationService;

    @PostMapping("/recommendation")
    public ResponseEntity<ClothingResponseDTO> recommendClothing(
            @RequestParam double temp,
            @RequestParam String userType) {

        // 추천 결과 생성
        ClothingResponseDTO responseDTO = recommendationService.recommendClothing(temp, userType);
        return ResponseEntity.ok(responseDTO);
    }
}