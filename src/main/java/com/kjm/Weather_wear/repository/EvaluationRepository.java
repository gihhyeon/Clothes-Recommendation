package com.kjm.Weather_wear.repository;

import com.kjm.Weather_wear.entity.Evaluation;
import com.kjm.Weather_wear.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    List<Evaluation> findByMember(MemberEntity member);
}