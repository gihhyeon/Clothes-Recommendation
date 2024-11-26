package com.kjm.Weather_wear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearnedDataDTO {
    private String memberEmail;
    private Double temperature;
    private List<RecommendationDTO> optimizedRecommendations;

    @Data
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecommendationDTO {
        private String type;
        private String item;
    }
}