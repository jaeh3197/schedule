package com.example.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {

    private String name;
    private long password;
    private String title;
    private String content;
    private String created_at;
    private String modified_at;

}
