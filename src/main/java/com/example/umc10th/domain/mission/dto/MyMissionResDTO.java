package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;

public class MyMissionResDTO {

    public record Response(
            String storeName,
            Integer rewardPoint,
            Integer conditionAmount,
            MissionStatus status,
            Boolean isSuccess
    ) {}
}