package com.miniproject.backend.global.exception;

import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.global.exception.base.CustomExceptionType;
import com.miniproject.backend.like.exception.LikeExceptionType;

public class GlobalException extends CustomException {

    private GlobalExceptionType globalExceptionType;

    public GlobalException(GlobalExceptionType globalExceptionType){
        this.globalExceptionType = globalExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.globalExceptionType;
    }
}
