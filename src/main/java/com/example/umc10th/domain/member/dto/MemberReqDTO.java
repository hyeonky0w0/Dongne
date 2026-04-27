package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.SocialType;
import com.example.umc10th.domain.mission.enums.Address;

import java.time.LocalDate;

public class MemberReqDTO {

    public record RequestBody(
            String name,
            String email,
            String phoneNumber,
            Gender gender,
            LocalDate birth,
            Address address,
            String detailAddress,
            SocialType socialType
    ) {}
}