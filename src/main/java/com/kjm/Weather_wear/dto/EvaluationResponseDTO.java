package com.kjm.Weather_wear.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.Weather_wear.entity.Evaluation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponseDTO {
    private String memberEmail;
    private double temperature;
    private int evaluationScore;
    private List<String> clothingRecommendations;

    public static EvaluationResponseDTO fromEntity(Evaluation evaluation) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> recommendations = new ArrayList<>();
        try {
            recommendations = objectMapper.readValue(evaluation.getClothingRecommendations(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            // JSON 파싱 실패 시 빈 리스트 반환
        }

        return new EvaluationResponseDTO(
                evaluation.getMember().getMemberEmail(),
                evaluation.getTemp(),
                evaluation.getEvaluationScore(),
                recommendations
        );
    }
}