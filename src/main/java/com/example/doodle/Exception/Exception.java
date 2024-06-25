package com.example.doodle.Exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Exception {
    private String error;
    private String message;

    public Exception error(String error, String message) {
        return Exception.builder()
                .error(error)
                .message(message)
                .build();
    }
}
