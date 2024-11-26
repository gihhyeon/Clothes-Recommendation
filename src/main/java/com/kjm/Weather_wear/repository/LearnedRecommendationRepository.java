
package com.kjm.Weather_wear.repository;

import com.kjm.Weather_wear.entity.LearnedRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearnedRecommendationRepository extends JpaRepository<LearnedRecommendation, Long> {
    List<LearnedRecommendation> findByMember_MemberEmail(String memberEmail);
}