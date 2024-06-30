package com.example.doodle.Login.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResponseDto {
    private String status;
    private String code;
    private String msg;
    private boolean success;
}
