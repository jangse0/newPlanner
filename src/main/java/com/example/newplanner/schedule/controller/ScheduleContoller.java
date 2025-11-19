package com.example.newplanner.schedule.controller;

import com.example.newplanner.common.exception.CustomException;
import com.example.newplanner.common.exception.ErrorCode;
import com.example.newplanner.schedule.dto.ScheduleRequest;
import com.example.newplanner.schedule.dto.ScheduleResponse;
import com.example.newplanner.schedule.service.ScheduleService;
import com.example.newplanner.user.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class ScheduleContoller {

    private final ScheduleService scheduleService;

    //일정 생성(로그인 확인 추가)
    @PostMapping("/api/schedules")
    public ResponseEntity<ScheduleResponse> createSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestBody ScheduleRequest request) {

        checkedLogin(sessionUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.createSchedule(request));
    }

    //일정 조회(1개)
    @GetMapping("/api/schedules/{id}")
    public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.getSchedule(id));
    }

    //일정 수정(로그인 추가)
    @PutMapping("/api/schedules/{id}")
    public ResponseEntity<ScheduleResponse> updateSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long id,
            @RequestBody ScheduleRequest request) {
        checkedLogin(sessionUser);

        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.updateSchedule(id, request));
    }

    //일정 삭제(로그인 추가)
    @DeleteMapping("/api/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long id) {
        checkedLogin(sessionUser);

        scheduleService.deleteSchedule(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //로그인 상태 확인
    private void checkedLogin(SessionUser user) {
        if (user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }
}
