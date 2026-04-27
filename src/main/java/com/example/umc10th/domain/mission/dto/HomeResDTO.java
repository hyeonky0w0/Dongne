package com.example.umc10th.domain.mission.dto;


public record HomeResDTO(
        String locationName,
        Integer point,
        Integer completedMissionCount
) {}