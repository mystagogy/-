package com.miniproject.backend.user.exception;

import com.miniproject.backend.global.exception.base.CustomExceptionType;

public enum UserExceptionType implements CustomExceptionType {
    DUPLICATION_EMAIL(-101,"중복된 이메일 입니다."),
    NOT_EMAIL_FORMAT(-102, "이메일 형식이 아닙니다."),
    ACCOUNT_NOT_MATCH(-103, "이메일 또는 비밀번호가 일치하지 않습니다.");

    private int errorCode;
    private String errorMsg;

    UserExceptionType(int errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getMessage() {
        return this.errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }
}
