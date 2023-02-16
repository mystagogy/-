package com.miniproject.backend.like.exception;

import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.global.exception.base.CustomExceptionType;
import com.miniproject.backend.loanproduct.exception.ProductExceptionType;

public class LikeException extends CustomException {

    private LikeExceptionType likeExceptionType;

    public LikeException(LikeExceptionType likeExceptionType){
        this.likeExceptionType = likeExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.likeExceptionType;
    }
}
