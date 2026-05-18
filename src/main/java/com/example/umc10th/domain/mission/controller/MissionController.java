package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.*;
import com.example.umc10th.domain.mission.enums.MissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.dto.PageResponse;   // ✅ 추가
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<HomeResDTO>> getHomeInfo(
            @RequestParam Long memberId
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(missionService.getHomeInfo(memberId))
        );
    }

    @GetMapping("/home/missions")
    public ResponseEntity<ApiResponse<PageResponse<MissionResDTO>>> getMissions(
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

    @GetMapping("/members/{memberId}/my-missions")
    public ResponseEntity<ApiResponse<PageResponse<MyMissionResDTO.Response>>> getMyMissions(
            @PathVariable Long memberId,
            @RequestParam(defaultValue = "ALL") MissionStatus status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size
    ) {
        return ResponseEntity.ok(
                ApiResponse.onSuccess(
                        missionService.getMyMissions(memberId, status, page, size)
                )
        );
    }


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