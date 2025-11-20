package com.example.newplanner.user.service;

import com.example.newplanner.common.config.PasswordEncoder;
import com.example.newplanner.common.entity.User;
import com.example.newplanner.common.exception.CustomException;
import com.example.newplanner.common.exception.ErrorCode;
import com.example.newplanner.user.SessionUser;
import com.example.newplanner.user.dto.LoginRequest;
import com.example.newplanner.user.dto.UserCreateRequest;
import com.example.newplanner.user.dto.UserResponse;
import com.example.newplanner.user.dto.UserUpdateRequest;
import com.example.newplanner.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //유저 생성
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {


        userRepository.findByEmail(request.getEmail())
                .ifPresent(u -> {
                    throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
                });


        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail()
        );

        User saved = userRepository.save(user);

        return new UserResponse(saved);
    }

    public UserResponse getUser(Long id) {
        User user = findUser(id);
        return new UserResponse(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    //유저 수정
    @Transactional
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = findUser(id);

        user.updateUser(request.getUsername(), request.getEmail());

        return new UserResponse(user);
    }

    //유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }


    //로그인
    @Transactional
    public SessionUser login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        //비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        return new SessionUser(user.getId(), user.getEmail());
    }

}
