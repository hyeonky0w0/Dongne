package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.Security.entity.AuthMember;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/members")
    public ResponseEntity<ApiResponse<MemberResDTO>> createMember(
            @RequestBody @Valid MemberReqDTO.SignUpRequest dto  // @Valid 추가
    ) {
        MemberResDTO response = memberService.createMember(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)           // 201 Created
                .body(ApiResponse.onSuccess(response));
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<MemberResDTO>> getMemberInfo(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(memberService.getMemberInfo(memberId))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberResDTO>> login(
            @RequestBody @Valid MemberReqDTO.LoginRequest dto,
            HttpServletRequest request
    ) {
        MemberResDTO response = memberService.login(dto);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return ResponseEntity.ok(ApiResponse.onSuccess(response));
    }

}