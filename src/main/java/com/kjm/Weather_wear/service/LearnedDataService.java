package com.kjm.Weather_wear.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.Weather_wear.dto.LearnedDataDTO;
import com.kjm.Weather_wear.entity.LearnedRecommendation;
import com.kjm.Weather_wear.entity.MemberEntity;
import com.kjm.Weather_wear.repository.LearnedRecommendationRepository;
import com.kjm.Weather_wear.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearnedDataService {

    private final LearnedRecommendationRepository learnedRecommendationRepository;
    private final MemberRepository memberRepository;

    public void saveLearnedData(LearnedDataDTO learnedDataDTO) {
        // 1. Member 조회
        MemberEntity member = memberRepository.findByMemberEmail(learnedDataDTO.getMemberEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + learnedDataDTO.getMemberEmail()));

        // 2. 학습된 추천 데이터 저장
        String optimizedClothingJson = convertToJson(learnedDataDTO.getOptimizedRecommendations());

        LearnedRecommendation recommendation = LearnedRecommendation.builder()
                .member(member)
                .temperature(learnedDataDTO.getTemperature())
                .optimizedClothing(optimizedClothingJson)
                .build();

        // 3. 저장
        learnedRecommendationRepository.save(recommendation);
    }

    private String convertToJson(List<LearnedDataDTO.RecommendationDTO> recommendations) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(recommendations);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting recommendations to JSON", e);
        }
    }
}