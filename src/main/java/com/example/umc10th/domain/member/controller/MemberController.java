package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public ResponseEntity<ApiResponse<MemberResDTO>> createMember(
            @RequestBody MemberReqDTO.RequestBody dto
    ) {
        MemberResDTO response = memberService.createMember(dto);
        
        return ResponseEntity.ok(
                ApiResponse.onSuccess(response)
        );
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<ApiResponse<MemberResDTO>> getMemberInfo(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(memberService.getMemberInfo(memberId))
        );
    }


}
