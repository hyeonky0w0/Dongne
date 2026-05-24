package com.example.umc10th.domain.member.dto;

public record MemberResDTO(
        Long id,
        String name,
        String email,
        String phoneNumber,
        Integer point
) {
    public record GetInfo(Long id, String name, String email) {}
    public record Login(String accessToken) {}
}


