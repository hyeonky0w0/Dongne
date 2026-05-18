package com.example.umc10th.domain.mission.dto;

import com.example.umc10th.domain.mission.enums.MissionStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MyMissionReqDTO {

    public record Filter(
            @NotNull MissionStatus status
    ) {}
}