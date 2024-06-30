package com.example.doodle.Exception;


import com.example.doodle.Login.Dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends Throwable {
    @ExceptionHandler(BadRequestException.class)
    public SignupResponseDto handlerRequestException() {;
        return new SignupResponseDto(Error.FAIL_TO_LOGIN.getHttpStatus(),Error.FAIL_TO_LOGIN.getCode(),Error.FAIL_TO_LOGIN.getMessage(), false);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Exception handleRequestException(UsernameNotFoundException exception) {
        return new Exception(Error.FAIL_TO_LOGIN.getHttpStatus(),Error.FAIL_TO_LOGIN.getCode(),Error.FAIL_TO_LOGIN.getMessage(), exception.getLocalizedMessage(),false);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Exception handleIllegalArgumentException(IllegalArgumentException exception) {
        return new Exception(Error.UN_MATCH_INFO.getHttpStatus(), Error.UN_MATCH_INFO.getCode(), Error.UN_MATCH_INFO.getMessage(), exception.getLocalizedMessage(),false);
    }

}
