package com.example.newplanner.common.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedules")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule extends BaseEntity{
    //일정 제목
    @Column(nullable = false)
    private String title;

    //일정 내용
    @Column(nullable = false)
    private String content;

    //N:1 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Schedule(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }


    public void update(String title, String content) {
        this.title = title;
        this.content = content;

    }



}
