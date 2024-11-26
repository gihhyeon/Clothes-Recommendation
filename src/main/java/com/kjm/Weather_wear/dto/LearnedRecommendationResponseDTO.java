package com.kjm.Weather_wear.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearnedRecommendationResponseDTO {

    private String memberEmail;
    private Double temperature;
    private String optimizedClothing; // JSON 형태의 추천 의류 목록

}