package com.example.umc10th.domain.member.converter;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.global.Security.dto.OAuthDTO;

public class MemberConverter {

    public static MemberResDTO toDTO(Member member) {
        return new MemberResDTO(
                member.getId(), member.getName(), member.getEmail(),
                member.getPhoneNumber(), member.getPoint()
        );
    }

    public static MemberResDTO.GetInfo toGetInfo(Member member) {
        return new MemberResDTO.GetInfo(
                member.getId(), member.getName(), member.getEmail()
        );
    }

    public static MemberResDTO.Login toLogin(String accessToken) {
        return new MemberResDTO.Login(accessToken);
    }

    public static Member toMember(OAuthDTO dto) {
        return Member.builder()
                .name(dto.getName())
                .email(dto.getSocialEmail())
                .socialUid(dto.getSocialUid())
                .socialType(dto.getSocialType())
                .point(0)
                .address(null)
                .gender(null)
                .birth(null)
                .phoneNumber(null)
                .password(null)
                .build();
    }
}