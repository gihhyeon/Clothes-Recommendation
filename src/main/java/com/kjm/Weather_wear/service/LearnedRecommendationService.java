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
     * 특정 사용자의 학습된 추천 결과 조회
     * 
     * @param memberEmail 사용자 이메일
     * @return 추천 결과 리스트
     */
    public List<LearnedRecommendationResponseDTO> getRecommendationsByMember(String memberEmail) {
        MemberEntity member = memberRepository.findByMemberEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + memberEmail));

        List<LearnedRecommendation> recommendations = learnedRecommendationRepository.findByMember_MemberEmail(memberEmail);

        return recommendations.stream()
                .map(rec -> new LearnedRecommendationResponseDTO(
                        member.getMemberEmail(),
                        rec.getTemperature(),
                        rec.getOptimizedClothing()
                ))
                .toList();
    }
}