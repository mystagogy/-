package com.miniproject.backend.shoppingbasket.exception;

import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.global.exception.base.CustomExceptionType;
import com.miniproject.backend.like.exception.LikeExceptionType;

public class BasketException extends CustomException {

    private BasketExceptionType basketExceptionType;

    public BasketException(BasketExceptionType basketExceptionType){
        this.basketExceptionType = basketExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.basketExceptionType;
    }
}
