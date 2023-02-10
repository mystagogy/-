package com.miniproject.backend.global.exception;

import com.miniproject.backend.global.dto.ErrorDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class CustomExceptionHandler {

    // 400, 401, 403, 404 에러 잡는 핸들러
    @ExceptionHandler
    public ErrorDTO globalHandle(GlobalExceptionType e){
        return ErrorDTO.builder()
                .errorCode(e.getErrorCode())
                .errorMessage(e.getMessage())
                .build();
    }

    //500 에러 잡는 핸들러
    @ExceptionHandler
    public ErrorDTO exception(Exception e) {
        return ErrorDTO.builder()
                .errorCode(GlobalExceptionType.INTERNAL_ERROR.getErrorCode())
                .errorMessage(GlobalExceptionType.INTERNAL_ERROR.getErrorMsg())
                .build();
    }



}
