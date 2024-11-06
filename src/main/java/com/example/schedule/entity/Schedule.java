package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Schedule {

    //필드값 생성
    @Setter
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

}
