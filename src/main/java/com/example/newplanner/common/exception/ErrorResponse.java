package com.example.newplanner.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private int status;      //http Status
    private String error;    //ErrorCode 이름
    private String message;  //메시지
}
