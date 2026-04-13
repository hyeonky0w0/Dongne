package com.example.umc10th.domain.mission.dto;

public class HomeResDTO {
    public record HomeDTO(
            String locationName,   // 동네명
            Integer point,       // 포인트
            Integer completedMissionCount // 완료 미션 개수
    ){}
}
