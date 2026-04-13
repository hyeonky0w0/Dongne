package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.CompleteMissionResDTO;
import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.dto.MyMissionResDTO;
import com.example.umc10th.domain.mission.entity.MissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    // 홈 상단 정보
    @GetMapping("/home")
    public ResponseEntity<ApiResponse<HomeResDTO>> getHomeInfo(
            @RequestParam Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(
                        missionService.getHomeInfo(memberId)
                )
        );
    }

    // 홈 미션 리스트
    @GetMapping("/home/missions")
    public ResponseEntity<ApiResponse<Page<MissionResDTO>>> getMissions(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "ONGOING") MissionStatus status,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(
                        missionService.getMissions(memberId, status, page, size)
                )
        );
    }

    // 내 미션
    @GetMapping("/my-missions")
    public ResponseEntity<ApiResponse<Page<MyMissionResDTO>>> getMyMissions(
            @RequestParam Long memberId,
            @RequestParam(required = false) MissionStatus status,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(
                        missionService.getMyMissions(memberId, status, page, size)
                )
        );
    }

    // 미션 완료
    @PostMapping("/my-missions/{myMissionId}/complete")
    public ResponseEntity<ApiResponse<CompleteMissionResDTO>> completeMission(
            @PathVariable Long myMissionId,
            @RequestParam Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(
                        missionService.completeMission(myMissionId, memberId)
                )
        );
    }
}