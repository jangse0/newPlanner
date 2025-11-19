package com.example.newplanner.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponse {
    private Long id;
    private String title;
    private String content;

    //작성자
    private Long userId;

    private String createdAt;
    private String updatedAt;
}

