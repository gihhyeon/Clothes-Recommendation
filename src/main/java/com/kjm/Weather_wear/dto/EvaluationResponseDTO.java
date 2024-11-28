package com.kjm.Weather_wear.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationResponseDTO {
    private String memberEmail;
    private double temperature;
    private int evaluationScore;
    private List<String> clothingRecommendations;

}