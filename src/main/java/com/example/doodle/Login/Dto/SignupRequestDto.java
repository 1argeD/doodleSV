package com.example.doodle.Login.Dto;

import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String passwordConfirm;

}
