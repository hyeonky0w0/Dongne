package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;

public class MemberConverter {

    public static MemberResDTO toDTO(Member member) {
        return new MemberResDTO(
                member.getName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getPoint()
        );
    }
}