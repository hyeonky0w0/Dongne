package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    public MemberReqDTO.RequestBody requestBody(MemberReqDTO.RequestBody dto) {

        return dto;
    }

    public MemberResDTO createMember(MemberReqDTO.RequestBody dto) {

        //  테스트용
        return new MemberResDTO(
                dto.name(),
                dto.email()
        );
    }
}