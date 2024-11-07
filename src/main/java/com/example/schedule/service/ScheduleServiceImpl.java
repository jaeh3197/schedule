package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    public List<ScheduleResponseDto> getAllSchedules() {

        return scheduleRepository.getAllSchedules();
    }

    @Override
    public ScheduleResponseDto findScheduleById(long id) {

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (optionalSchedule.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        return new ScheduleResponseDto(optionalSchedule.get());
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(
            long id, String name, long password, String title, String content, String modified_at
    ) {

        if (name == null || password == 0 || title == null || modified_at == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and password are required");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, name, password, title, content, modified_at);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (password != optionalSchedule.get().getPassword()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        return new ScheduleResponseDto(optionalSchedule.get());
    }

    @Transactional
    @Override
    public void deleteSchedule(long id, long password) {

        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);

        if (password != optionalSchedule.get().getPassword()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        int deletedRow = scheduleRepository.deleteSchedule(id, password);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }


    }
}