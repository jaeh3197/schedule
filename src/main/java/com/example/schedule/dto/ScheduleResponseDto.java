package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private long id;
    private String name;
    private String title;
    private String content;
    private String created_at;
    private String modified_at;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.name = schedule.getName();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.created_at = schedule.getCreated_at();
        this.modified_at = schedule.getModified_at();
    }
}
