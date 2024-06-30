package com.example.doodle.Exception;

import lombok.Getter;

@Getter
public enum Error implements ErrorCode {
    NOT_FOUND("404", "badStatus : Can't Found This Data", "해당 정보를 찾을 수 없습니다"),
    UN_MATCH_INFO("606", "badStatus : ", "넘겨받은 매개 변수가 일치하지 않습니다"),
    NULL_POINT_EXCEPTION("800", "badStatus", "해당 정보가 null 값입니다"),
    FAIL_TO_LOGIN("500","badStatus","로그인에 실패하였습니다. 아이디와 비밀번호를 확인해 주세요"),

    ;
    private final String httpStatus;
    private final String code;
    private final String message;

    Error(String httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
