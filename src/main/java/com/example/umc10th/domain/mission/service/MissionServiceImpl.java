package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberErrorCode;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.CompleteMissionResDTO;
import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.dto.MyMissionResDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.global.apiPayload.exception.ProjectException;
import com.example.umc10th.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionServiceImpl implements MissionService {

    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    @Override
    public HomeResDTO getHomeInfo(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        int completedCount = memberMissionRepository.countCompletedByMemberId(memberId);

        return MissionConverter.toHomeDTO(member, completedCount);
    }

    @Override
    public PageResponse<MissionResDTO> getMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    ) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ProjectException(MemberErrorCode.MEMBER_NOT_FOUND));

        Address address = member.getAddress();
        Pageable pageable = PageRequest.of(page, size);
        boolean isComplete = (status == MissionStatus.COMPLETED);

        Page<MemberMission> missions = memberMissionRepository
                .findMissionsByMemberAndAddress(memberId, address, isComplete, pageable);


        return PageResponse.from(missions.map(MissionConverter::toMissionDTO));
    }


    @Override
    public PageResponse<MyMissionResDTO.Response> getMyMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Boolean isComplete = (status == null) ? null : (status == MissionStatus.COMPLETED);

        Page<MemberMission> result = memberMissionRepository
                .findByMemberIdAndStatus(memberId, isComplete, pageable);

        return PageResponse.from(result.map(MissionConverter::toMyMissionDTO));
    }

    @Override
    @Transactional
    public CompleteMissionResDTO completeMission(Long myMissionId, Long memberId) {
        MemberMission mm = memberMissionRepository.findById(myMissionId)
                .orElseThrow(() -> new ProjectException(MissionErrorCode.MISSION_NOT_FOUND));

        if (!mm.getMember().getId().equals(memberId)) {
            throw new ProjectException(MissionErrorCode.MISSION_UNAUTHORIZED);
        }
        if (mm.isComplete()) {
            throw new ProjectException(MissionErrorCode.MISSION_ALREADY_COMPLETED);
        }

        mm.markAsCompleted();

        return new CompleteMissionResDTO(
                mm.getId(),
                true,
                mm.getMission().getRewardPoint()
        );
    }
}