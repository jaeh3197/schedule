package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> getAllSchedules(String name, String modified_at);

    Schedule findScheduleById(long id);

    void deleteSchedule(long id);
}
