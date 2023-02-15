package com.miniproject.backend.global.exception;

import com.miniproject.backend.global.dto.ErrorDTO;
import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.user.exception.UserException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler implements ErrorController {

    //custom exception 처리 핸들러
    @ExceptionHandler(value = UserException.class)
    public ErrorDTO handleUserException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

}
