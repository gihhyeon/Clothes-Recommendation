package com.kjm.Weather_wear.service;

import com.kjm.Weather_wear.dto.LearnedRecommendationResponseDTO;
import com.kjm.Weather_wear.entity.LearnedRecommendation;
import com.kjm.Weather_wear.entity.MemberEntity;
import com.kjm.Weather_wear.repository.LearnedRecommendationRepository;
import com.kjm.Weather_wear.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearnedRecommendationService {

    private final LearnedRecommendationRepository learnedRecommendationRepository;
    private final MemberRepository memberRepository;

    /**
     * 특정 사용자와 기온에 해당하는 학습된 추천 결과 조회
     *
     * @param memberEmail 사용자 이메일
     * @param temperature 요청한 기온
     * @return 추천 결과 리스트
     */
    public List<LearnedRecommendationResponseDTO> getRecommendationsByMemberAndTemperature(String memberEmail, Double temperature) {
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + memberEmail));

        // 특정 사용자와 기온에 맞는 데이터 필터링
        List<LearnedRecommendation> recommendations = learnedRecommendationRepository.findByMember_MemberEmail(memberEmail).stream()
                .filter(recommendation -> recommendation.getTemperature().equals(temperature))
                .toList();

        // DTO로 변환하여 반환
        return recommendations.stream()
                .map(rec -> new LearnedRecommendationResponseDTO(
                        member.getMemberEmail(),
                        rec.getTemperature(),
                        rec.getOptimizedClothing()
                ))
                .toList();
    }
}