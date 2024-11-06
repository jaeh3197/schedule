package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.*;

//Repository 생성
//DB 와 상호작용
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @Override
    public Schedule saveSchedule(Schedule schedule) {

        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;
        schedule.setId(scheduleId);

        scheduleList.put(scheduleId, schedule);

        return schedule;
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(String name, String modified_at) {

        // List 초기화
        List<ScheduleResponseDto> allSchedule = new ArrayList<>();

        for (Schedule schedule : scheduleList.values()) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
            allSchedule.add(responseDto);
        }
        return allSchedule;
    }

    @Override
    public Schedule findScheduleById(long id) {

        return scheduleList.get(id);
    }

    @Override
    public void deleteSchedule(long id) {
        scheduleList.remove(id);
    }
}
