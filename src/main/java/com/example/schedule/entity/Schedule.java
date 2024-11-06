package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    //필드값 생성
    private long id;
    private String name;
    private long password;
    private String title;
    private String content;
    private String created_at;
    private String modified_at;


}
