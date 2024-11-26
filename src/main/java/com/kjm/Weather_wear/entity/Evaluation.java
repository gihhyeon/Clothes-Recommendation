package com.kjm.Weather_wear.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double temp;

    private int evaluationScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email", referencedColumnName = "memberEmail", nullable = false)
    private MemberEntity member;

    @Column(name = "clothing_recommendations", columnDefinition = "TEXT")
    private String clothingRecommendations; // JSON 형태로 저장
}