package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.dto.HomeResDTO;
import com.example.umc10th.domain.mission.dto.MissionResDTO;
import com.example.umc10th.domain.mission.dto.MyMissionResDTO;
import com.example.umc10th.domain.mission.entity.MissionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService {

    @Override
    public HomeResDTO getHomeInfo(Long memberId) {
        return null; //테스트용
    }

    @Override
    public Page<MissionResDTO> getMissions(Long memberId, MissionStatus status, int page, int size) {
        return Page.empty(); //테스트용
    }

    @Override
    public Page<MyMissionResDTO> getMyMissions(
            Long memberId,
            MissionStatus status,
            int page,
            int size
    ) {
        return Page.empty(); // 테스트용
    }
}
