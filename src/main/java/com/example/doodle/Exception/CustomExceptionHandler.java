package com.example.doodle.Exception;


import com.example.doodle.Login.Dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final Exception exception;

    @ExceptionHandler(BadRequestException.class)
    public SignupResponseDto handlerRequestException(BadRequestException badRequestException) {
        return new SignupResponseDto(badRequestException.getMessage(), false);
    }


}
