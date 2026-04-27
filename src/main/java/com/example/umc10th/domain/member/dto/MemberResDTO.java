package com.example.umc10th.domain.member.dto;

public record MemberResDTO(
        String name,
        String email,
        String phoneNumber,
        Integer point
) {}