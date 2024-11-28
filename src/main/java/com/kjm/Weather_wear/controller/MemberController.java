package com.kjm.Weather_wear.controller;

import com.kjm.Weather_wear.dto.MemberDTO;
import com.kjm.Weather_wear.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원가입 요청
     * POST /api/members
     */
    @PostMapping
    public ResponseEntity<String> save(@RequestBody MemberDTO memberDTO) {
        boolean success = memberService.save(memberDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회원가입 실패");
        }

    }

    /**
     * 로그인 요청
     * POST /api/members/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberDTO memberDTO, HttpSession session) {
        MemberDTO loginResult = memberService.login(memberDTO);
        if (loginResult != null) {
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return ResponseEntity.ok(loginResult); // 로그인 성공 시 사용자 정보 반환
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }
    }

    /**
     * 회원 목록 조회
     * GET /api/members
     */
    @GetMapping
    public ResponseEntity<List<MemberDTO>> findAll() {
        List<MemberDTO> memberDTOList = memberService.findAll();
        return ResponseEntity.ok(memberDTOList); // HTTP 200 OK와 함께 데이터 반환
    }

    /**
     * 회원 상세 조회
     * GET /api/members/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        MemberDTO memberDTO = memberService.findById(id);
        if (memberDTO != null) {
            return ResponseEntity.ok(memberDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보가 존재하지 않습니다.");
        }
    }

    /**
     * 회원 정보 수정 요청
     * PUT /api/members
     */
    @PutMapping
    public ResponseEntity<String> update(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return ResponseEntity.ok("회원정보 수정 완료");
    }

    /**
     * 회원 삭제 요청
     * DELETE /api/members/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        memberService.deleteById(id);
        return ResponseEntity.ok("회원 삭제 완료");
    }

    /**
     * 로그아웃 요청
     * POST /api/members/logout
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 완료");
    }
}
