package com.kjm.Weather_wear.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRequestDTO {
    private String memberEmail; // 사용자의 이메일
    private int evaluationScore; // 평가 점수 (-1, 0, 1)
    private double temp; // 기온
}