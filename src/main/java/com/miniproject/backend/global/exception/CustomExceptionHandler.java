package com.miniproject.backend.global.exception;

import com.miniproject.backend.global.dto.ErrorDTO;
import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.like.exception.LikeException;
import com.miniproject.backend.loanproduct.exception.ProductException;
import com.miniproject.backend.shoppingbasket.exception.BasketException;
import com.miniproject.backend.user.exception.UserException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    @ExceptionHandler(value = ProductException.class)
    public ErrorDTO handleProductException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

    @ExceptionHandler(value = LikeException.class)
    public ErrorDTO handleLikeException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

    @ExceptionHandler(value = BasketException.class)
    public ErrorDTO handleBasketException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())
                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

    @ExceptionHandler(value = GlobalException.class)
    public ErrorDTO handleGlobalException(CustomException ce){
        return ErrorDTO.builder()
                .errorCode(ce.getExceptionType().getErrorCode())

                .errorMessage(ce.getExceptionType().getMessage())
                .build();
    }

}
