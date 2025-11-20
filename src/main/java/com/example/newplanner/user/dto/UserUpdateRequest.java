package com.example.newplanner.user.dto;


import lombok.Getter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Getter
public class UserUpdateRequest {

    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

}
