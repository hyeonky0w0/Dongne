package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.CompleteMissionResDTO;
import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.dto.MyMissionDTO;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));

        Address address = member.getAddress();
        Pageable pageable = PageRequest.of(page, size);
        boolean isComplete = (status == MissionStatus.COMPLETED);

        Page<MemberMission> missions = memberMissionRepository
                .findMissionsByMemberAndAddress(memberId, address, isComplete, pageable);


        return PageResponse.from(missions.map(MissionConverter::toMissionDTO));
    }


    @Override
    public PageResponse<MyMissionDTO.Response> getMyMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MemberMission> result = memberMissionRepository.findByMemberId(memberId, pageable);

        Page<MyMissionDTO.Response> mapped = result
                .map(MissionConverter::toMyMissionDTO)
                .map(dto -> (status == null || dto.status() == status) ? dto : null);

        return PageResponse.from(mapped);
    }

    @Override
    @Transactional
    public CompleteMissionResDTO completeMission(Long myMissionId, Long memberId) {
        MemberMission mm = memberMissionRepository.findById(myMissionId)
                .orElseThrow(() -> new IllegalArgumentException("미션을 찾을 수 없습니다."));

        if (!mm.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("본인의 미션만 완료할 수 있습니다.");
        }
        if (mm.isComplete()) {
            throw new IllegalStateException("이미 완료된 미션입니다.");
        }

        mm.complete();

        return new CompleteMissionResDTO(
                mm.getId(),
                true,
                mm.getMission().getRewardPoint()
        );
    }
}