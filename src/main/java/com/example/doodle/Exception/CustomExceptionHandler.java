package com.example.doodle.Exception;


import com.example.doodle.Login.Dto.SignupResponseDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public SignupResponseDto handlerRequestException(BadRequestException badRequestException) {
        return new SignupResponseDto(badRequestException.getMessage(), false);
    }

}
