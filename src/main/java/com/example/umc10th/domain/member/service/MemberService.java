package com.example.umc10th.domain.member.service;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResDTO createMember(MemberReqDTO.SignUpRequest dto){

        if (memberRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("이미 존재하는 이메일");
        }

        Member member = Member.builder()
                .name(dto.name())
                .email(dto.email())
                .phoneNumber(dto.phoneNumber())
                .point(0) // 초기값
                .gender(dto.gender())
                .birth(dto.birth())
                .address(dto.address())
                .detailAddress(dto.detailAddress())
                .socialType(dto.socialType())
                .build();

        memberRepository.save(member);

        return MemberConverter.toDTO(member);
    }

    //마이페이지 조회
    public MemberResDTO getMemberInfo(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member 없음"));

        return MemberConverter.toDTO(member);
    }
}