package com.example.newplanner.user.controller;

import com.example.newplanner.user.SessionUser;
import com.example.newplanner.user.dto.LoginRequest;
import com.example.newplanner.user.dto.UserCreateRequest;
import com.example.newplanner.user.dto.UserResponse;
import com.example.newplanner.user.dto.UserUpdateRequest;
import com.example.newplanner.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //유저 생성
    @PostMapping("/api/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    //id값이 유효하지 않을 수 있으니 body를 안쓰는게 좋다?
    //유저 정보 불러오기
    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<UserResponse>> getUsers() {

        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsers());
    }

    //유저 정보 수정
    @PutMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @PathVariable Long id, @RequestBody UserUpdateRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(id, request));
    }

    //유저 삭제
    @DeleteMapping("/api/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //로그인
    @PostMapping("/api/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request,
                                        HttpSession session) {

        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body("로그인 성공");
    }

}
