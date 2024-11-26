package com.kjm.Weather_wear.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LearnedRecommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearnedRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_email", referencedColumnName = "memberEmail", nullable = false)
    private MemberEntity member; // MemberEntity와 연결

    @Column(nullable = false)
    private Double temperature;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String optimizedClothing; // JSON 형태로 저장
}