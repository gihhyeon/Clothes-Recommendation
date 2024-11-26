package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.LearnedRecommendationResponseDTO;
import com.kjm.Weather_wear.service.LearnedRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class LearnedRecommendationController {

    private final LearnedRecommendationService learnedRecommendationService;

    /**
     * 특정 사용자의 학습된 추천 결과 조회
     * 
     * @param memberEmail 사용자 이메일
     * @return 추천 결과 리스트
     */
    @GetMapping("/{memberEmail}")
    public ResponseEntity<List<LearnedRecommendationResponseDTO>> getRecommendationsByMember(
            @PathVariable String memberEmail) {

        log.info("Received id : {}", memberEmail);
        try {
            List<LearnedRecommendationResponseDTO> recommendations = learnedRecommendationService.getRecommendationsByMember(memberEmail);
            return ResponseEntity.ok(recommendations);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}