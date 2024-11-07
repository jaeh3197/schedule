package com.example.schedule.controller;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    //Schedule 클래스 호출
    private final ScheduleService scheduleService;

    //생성자 주입
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //일정 생성
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {

        //Service Layer 호출, 응답
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
    }

    //일정 전체 조회
    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {

        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }

    //선택 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getSchedule(@PathVariable long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    //선택한 일정 수정
    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable long id,
            @RequestBody ScheduleRequestDto dto
    ) {

        return new ResponseEntity<>(scheduleService.updateSchedule(
                id, dto.getName(), dto.getPassword(), dto.getTitle(),
                dto.getContent(), dto.getModified_at()), HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable long id, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteSchedule(id, dto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
