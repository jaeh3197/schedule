package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> getAllSchedules(String name, String modified_at) {

        return scheduleRepository.getAllSchedules(name, modified_at);
    }

    @Override
    public ScheduleResponseDto findScheduleById(long id) {

        Schedule schedule = scheduleRepository.findScheduleById(id);

        if (schedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto updateSchedule(
            long id, String name, long password, String title, String content, String modified_at
    ) {
        Schedule schedule = scheduleRepository.findScheduleById(id);

        if (schedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        if (password != schedule.getPassword()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        schedule.updateSchedule(name, title, content, modified_at);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(long id, long password) {

        Schedule schedule = scheduleRepository.findScheduleById(id);

        if (schedule == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        if (password != schedule.getPassword()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        scheduleRepository.deleteSchedule(id);
    }


}
