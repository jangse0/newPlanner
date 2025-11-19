package com.example.newplanner.schedule.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleRequest {
    private String title;
    private String content;
    private Long userId;


}

