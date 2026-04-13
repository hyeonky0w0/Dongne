package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public MemberReqDTO.RequestBody requestBody(MemberReqDTO.RequestBody dto) {

        return dto;
    }
}