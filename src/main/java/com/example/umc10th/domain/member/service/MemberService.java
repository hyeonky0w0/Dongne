package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResDTO createMember(MemberReqDTO.SignUpRequest dto) {

        if (memberRepository.existsByEmail(dto.email())) {
            throw new ProjectException(MemberErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Member member = Member.builder()
                .name(dto.name())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password())) // BCrypt 암호화
                .phoneNumber(dto.phoneNumber())
                .point(0)
                .gender(dto.gender())
                .birth(dto.birth())
                .address(dto.address())
                .detailAddress(dto.detailAddress())
                .socialUid(dto.socialUid())
                .socialType(dto.socialType())
                .build();

        memberRepository.save(member);
        return MemberConverter.toDTO(member);
    }

    public MemberResDTO getMemberInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toDTO(member);
    }
}