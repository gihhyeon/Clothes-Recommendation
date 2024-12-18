package com.kjm.Weather_wear.entity;

import com.kjm.Weather_wear.dto.MemberDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "member_table")
public class MemberEntity {
    @Id // pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment(자동으로 값 1씩 증가)
    private Long id;

    @Column(unique = true) // unique 제약 조건 추가
    private String memberEmail; // 아이디

    @Column
    private String memberPassword; // 비밀번호

    @Column
    private String memberName; // 이름

    @Column
    private String userType; // 유저 타입

    @Builder
    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.id = memberDTO.getId();
        memberEntity.memberEmail = memberDTO.getMemberEmail();
        memberEntity.memberPassword = memberDTO.getMemberPassword();
        memberEntity.memberName = memberDTO.getMemberName();
        memberEntity.userType = memberDTO.getUserType();

        return memberEntity;
    }


    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.id = memberDTO.getId();
        memberEntity.memberEmail = memberDTO.getMemberEmail();
        memberEntity.memberPassword = memberDTO.getMemberPassword();
        memberEntity.memberName = memberDTO.getMemberName();
        memberEntity.userType = memberDTO.getUserType();

        return memberEntity;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Evaluation> evaluations = new ArrayList<>();


}
