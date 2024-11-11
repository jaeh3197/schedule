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

    //일정 생성 로직
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청 받은 데이터로 객체 생성
        Schedule schedule = new Schedule(dto.getName(), dto.getPassword(), dto.getTitle(),
                dto.getContent(), dto.getCreated_at(), dto.getModified_at()
        );

        //DB 저장
        return scheduleRepository.saveSchedule(schedule);
    }

    //전체 일정 조회 로직
    @Override
    public List<ScheduleResponseDto> getAllSchedules(String name, String modified_at) {

        return scheduleRepository.getAllSchedules(name, modified_at);
    }

    //선택 일정 조회 로직
    @Override
    public ScheduleResponseDto findScheduleById(long id) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    //선택 일정 수정 로직
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(
            long id, String name, long password, String title, String content, String modified_at
    ) {

        boolean requiredFieldsAreMissing = name == null || password == 0 || title == null || content == null;
        if (requiredFieldsAreMissing) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name and password are required");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, name, password, title, content, modified_at);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        validatePassword(schedule.getPassword(), password);

        return new ScheduleResponseDto(schedule);
    }

    //선택 일정 삭제 로직
    @Transactional
    @Override
    public void deleteSchedule(long id, long password) {

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);

        validatePassword(schedule.getPassword(), password);

        int deletedRow = scheduleRepository.deleteSchedule(id, password);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }

    //비밀번호 검증
    private void validatePassword(long sourcePassword, long targetPassword) {
        if (sourcePassword != targetPassword) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
    }
}