package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> getAllSchedules();

    Optional<Schedule> findScheduleById(long id);

    int updateSchedule(Long id, String name, long password, String title, String content, String modified_at);

    int deleteSchedule(long id, long password);
}
