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

    public Schedule(String name, long password, String title, String content, String created_at, String modified_at) {
        this.name = name;
        this.password = password;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }

    public void updateSchedule(String name, String title, String content, String modified_at) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.modified_at = modified_at;
    }

}
