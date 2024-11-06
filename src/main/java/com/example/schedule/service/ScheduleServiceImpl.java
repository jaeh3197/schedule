package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

//Service 어노테이션 생성
//비지니스 로직 구현
@Service
public class ScheduleServiceImpl implements ScheduleService {

    //Repository 호출
    private final ScheduleRepository scheduleRepository;

    //생성자 주입
    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청 받은 데이터로 객체 생성
        Schedule schedule = new Schedule(dto.getName(), dto.getPassword(), dto.getTitle(),
                dto.getContent(), dto.getCreated_at(), dto.getModified_at()
        );

        //DB 저장
        Schedule savedSchedule = scheduleRepository.saveSchedule(schedule);

        return new ScheduleResponseDto(savedSchedule);
    }
}
