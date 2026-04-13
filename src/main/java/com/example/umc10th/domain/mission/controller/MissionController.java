package com.example.umc10th.domain.mission.controller;

import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MissionStatus;
import com.example.umc10th.domain.mission.service.MissionService;
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
    public ResponseEntity<HomeResDTO> getHomeInfo(
            @RequestParam Long memberId
    ) {
        HomeResDTO response = missionService.getHomeInfo(memberId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/home/missions")
    public ResponseEntity<Page<MissionResDTO>> getMissions(
            @RequestParam Long memberId,
            @RequestParam(required = false) MissionStatus status,
            @RequestParam int page,
            @RequestParam int size
    ) {
        Page<MissionResDTO> response =
                missionService.getMissions(memberId, status, page, size);

        return ResponseEntity.ok(response);
    }



}