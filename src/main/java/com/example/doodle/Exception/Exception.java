package com.example.doodle.Exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Exception extends Throwable{
    private String status;
    private String code;
    private String message;
    private String localizedMessage;
    private boolean success;

    public Exception error(String status,String code, String message, String localizedMessage ,boolean success) {
        return Exception.builder()
                .success(success)
                .code(code)
                .status(status)
                .message(message)
                .localizedMessage(localizedMessage)
                .build();
    }
}
