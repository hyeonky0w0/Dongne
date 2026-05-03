package com.example.umc10th.domain.mission.dto;


public record CompleteMissionResDTO(
        Long myMissionId,
        Boolean isCompleted,
        Integer rewardPoint
) {}

