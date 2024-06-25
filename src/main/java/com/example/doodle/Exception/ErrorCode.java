package com.example.doodle.Exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    String code();
    HttpStatus getHttpStatus();
    String getMessage();

}
