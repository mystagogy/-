package com.miniproject.backend.like.exception;

import com.miniproject.backend.global.exception.base.CustomExceptionType;

public enum LikeExceptionType implements CustomExceptionType {
    EXIST_LIKE(-301,"이미 등록된 관심상품입니다.");

    private int errorCode;
    private String errorMsg;

    LikeExceptionType(int errorCode, String errorMsg){
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
