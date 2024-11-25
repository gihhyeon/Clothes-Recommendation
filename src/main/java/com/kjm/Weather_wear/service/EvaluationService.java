package com.kjm.Weather_wear.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.Weather_wear.entity.Evaluation;
import com.kjm.Weather_wear.entity.MemberEntity;
import com.kjm.Weather_wear.repository.ClothingRepository;
import com.kjm.Weather_wear.repository.EvaluationRepository;
import com.kjm.Weather_wear.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final MemberRepository memberRepository;
    private final ClothingRepository clothingRepository;

    public Evaluation saveEvaluation(String memberEmail, int evaluationScore, double temp) {
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + memberEmail));

        List<String> clothingRecommendations = clothingRepository.findAllByTemperatureRange(temp).stream()
                .map(clothing -> String.format("%s (%s°C to %s°C)", clothing.getItemName(), clothing.getMinTemp(), clothing.getMaxTemp()))
                .toList();

        String recommendationsJson = convertListToJson(clothingRecommendations);

        Evaluation evaluation = Evaluation.builder()
                .member(member)
                .temp(temp)
                .evaluationScore(evaluationScore)
                .clothingRecommendations(recommendationsJson)
                .build();

        return evaluationRepository.save(evaluation);
    }

    public List<Evaluation> getEvaluationsByMember(String memberEmail) {
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + memberEmail));

        return evaluationRepository.findByMember(member);
    }

    /**
     * 모든 평가 조회
     */
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    private String convertListToJson(List<String> list) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting list to JSON", e);
        }
    }
}