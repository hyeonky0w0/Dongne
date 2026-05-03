package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.CompleteMissionResDTO;
import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.dto.MyMissionResDTO;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.global.dto.PageResponse;

public interface MissionService {

    // 홈 상단 정보
    HomeResDTO getHomeInfo(Long memberId);

    // 홈 미션 목록 (지역 기반, 페이징, 상태 필터)
    PageResponse<MissionResDTO> getMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    );

    // 내 미션 목록
    PageResponse<MyMissionResDTO.Response> getMyMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    );

    // 미션 완료 처리
    CompleteMissionResDTO completeMission(Long myMissionId, Long memberId);
}