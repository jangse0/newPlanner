package com.example.newplanner.schedule.service;

import com.example.newplanner.common.entity.Schedule;
import com.example.newplanner.common.entity.User;
import com.example.newplanner.common.exception.CustomException;
import com.example.newplanner.common.exception.ErrorCode;
import com.example.newplanner.schedule.dto.ScheduleRequest;
import com.example.newplanner.schedule.dto.ScheduleResponse;
import com.example.newplanner.schedule.repository.ScheduleRepository;
import com.example.newplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public ScheduleResponse createSchedule(Long userId, ScheduleRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getUser().getId(),
                savedSchedule.getCreatedAt().toString(),
                savedSchedule.getUpdatedAt().toString()
        );
    }

    // 일정 조회 (단건)
    public ScheduleResponse getSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        return new ScheduleResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getId(),
                schedule.getCreatedAt().toString(),
                schedule.getUpdatedAt().toString()
        );
    }

    // 일정 수정
    @Transactional
    public ScheduleResponse updateSchedule(Long userId, Long id, ScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 수정
        schedule.update(request.getTitle(), request.getContent());

        Schedule updated = scheduleRepository.save(schedule);

        return new ScheduleResponse(
                updated.getId(),
                updated.getTitle(),
                updated.getContent(),
                updated.getUser().getId(),
                updated.getCreatedAt().toString(),
                updated.getUpdatedAt().toString()
        );
    }

    // 일정 삭제
    @Transactional
    public void deleteSchedule(Long userId, Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));


        //일정 작성자와 로그인한 사용자가 같은지 확인
        if (!schedule.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        scheduleRepository.delete(schedule);
    }
}

