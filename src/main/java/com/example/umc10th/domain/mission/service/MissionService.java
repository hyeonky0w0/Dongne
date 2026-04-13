package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.entity.MissionStatus;
import org.springframework.data.domain.Page;

public interface MissionService {

    HomeResDTO getHomeInfo(Long memberId);

    Page<MissionResDTO> getMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    );
}