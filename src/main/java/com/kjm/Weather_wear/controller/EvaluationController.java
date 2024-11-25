package com.kjm.Weather_wear.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjm.Weather_wear.dto.EvaluationRequestDTO;
import com.kjm.Weather_wear.dto.EvaluationResponseDTO;
import com.kjm.Weather_wear.entity.Evaluation;
import com.kjm.Weather_wear.service.EvaluationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;
    private final ObjectMapper objectMapper;

    @PostMapping("/submit")
    public ResponseEntity<EvaluationResponseDTO> submitEvaluation(@RequestBody EvaluationRequestDTO request) {
        try {
            Evaluation evaluation = evaluationService.saveEvaluation(
                    request.getMemberEmail(),
                    request.getEvaluationScore(),
                    request.getTemp()
            );

            // JSON 문자열을 List<String>으로 변환
            List<String> clothingRecommendations = convertJsonToList(evaluation.getClothingRecommendations());

            EvaluationResponseDTO response = new EvaluationResponseDTO(
                    evaluation.getMember().getMemberEmail(),
                    evaluation.getTemp(),
                    evaluation.getEvaluationScore(),
                    clothingRecommendations
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error submitting evaluation: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<EvaluationResponseDTO>> getAllEvaluations() {
        try {
            List<Evaluation> evaluations = evaluationService.getAllEvaluations();

            List<EvaluationResponseDTO> response = evaluations.stream().map(evaluation -> {
                List<String> clothingRecommendations = convertJsonToList(evaluation.getClothingRecommendations());
                return new EvaluationResponseDTO(
                        evaluation.getMember().getMemberEmail(),
                        evaluation.getTemp(),
                        evaluation.getEvaluationScore(),
                        clothingRecommendations
                );
            }).toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving evaluations: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * 특정 회원의 평가 데이터를 조회
     *
     * @param memberEmail 회원 이메일
     * @return 해당 회원의 평가 데이터 리스트
     */
    @GetMapping("/{memberEmail}")
    public ResponseEntity<List<EvaluationResponseDTO>> getEvaluationsByMember(@PathVariable String memberEmail) {
        try {
            List<Evaluation> evaluations = evaluationService.getEvaluationsByMember(memberEmail);

            List<EvaluationResponseDTO> response = evaluations.stream().map(evaluation -> {
                List<String> clothingRecommendations = convertJsonToList(evaluation.getClothingRecommendations());
                return new EvaluationResponseDTO(
                        evaluation.getMember().getMemberEmail(),
                        evaluation.getTemp(),
                        evaluation.getEvaluationScore(),
                        clothingRecommendations
                );
            }).toList();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error retrieving evaluations for member {}: {}", memberEmail, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    /**
     * JSON 문자열을 List<String>으로 변환
     */
    private List<String> convertJsonToList(String json) {
        try {
            if (json == null || json.isBlank() || json.equals("[]")) {
                return new ArrayList<>(); // 빈 리스트 반환
            }
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            log.error("Error parsing JSON to List<String>: {}", e.getMessage(), e);
            return new ArrayList<>(); // 파싱 실패 시 빈 리스트 반환
        }
    }
}