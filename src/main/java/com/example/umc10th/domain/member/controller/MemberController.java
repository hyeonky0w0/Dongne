package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    public MemberReqDTO.RequestBody requestBody(
            @RequestBody MemberReqDTO.RequestBody dto
    ){
        return memberService.requestBody(dto);
    }


}
