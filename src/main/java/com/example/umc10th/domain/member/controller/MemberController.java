package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.Security.entity.AuthMember;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<ApiResponse<MemberResDTO>> createMember(
            @RequestBody @Valid MemberReqDTO.SignUpRequest dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(memberService.createMember(dto)));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<MemberResDTO>> getMemberInfo(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.getMemberInfo(memberId)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResDTO.Login>> login(
            @RequestBody @Valid MemberReqDTO.LoginRequest dto
    ) {
        return ResponseEntity.ok(ApiResponse.onSuccess(memberService.login(dto)));
    }

    @GetMapping("/v2/members/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @AuthenticationPrincipal AuthMember member
    ) {
        return ApiResponse.onSuccess(memberService.getInfo(member));
    }
}