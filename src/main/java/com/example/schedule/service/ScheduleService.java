package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> getAllSchedules(String name, String modified_at);

    ScheduleResponseDto findScheduleById(long id);

    ScheduleResponseDto updateSchedule(
            long id, String name, long password, String title, String content, String modified_at);

    void deleteSchedule(long id, long password);
}
