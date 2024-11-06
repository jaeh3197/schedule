package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    //요청 받은 데이터를 임시로 저장할 Map 생성
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    //Schedule 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

        // 식별자가 1씩 증가
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        // 입력 받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(
                scheduleId, dto.getName(), dto.getPassword(), dto.getTitle(),
                dto.getContent(), dto.getCreated_at(), dto.getModified_at()
        );

        // Map 에 객체 저장
        scheduleList.put(scheduleId, schedule);

        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.CREATED);
    }
}
