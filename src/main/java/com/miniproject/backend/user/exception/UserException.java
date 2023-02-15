package com.miniproject.backend.user.exception;

import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.global.exception.base.CustomExceptionType;

public class UserException extends CustomException {

    private UserExceptionType userExceptionType;

    public UserException(UserExceptionType userExceptionType){
        this.userExceptionType = userExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.userExceptionType;
    }
}
