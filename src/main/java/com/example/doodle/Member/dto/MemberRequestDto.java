package com.example.doodle.Member.dto;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
public class MemberRequestDto {
    private String email;
    private String nickname;
    private String password;
    private String passwordConfirm;
}
