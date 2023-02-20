package com.miniproject.backend.shoppingbasket.exception;

import com.miniproject.backend.global.exception.base.CustomExceptionType;

public enum BasketExceptionType implements CustomExceptionType {

    EXIST_BASKET(-401,"이미 장바구니에 등록된 상품입니다."),
    NOT_EXIST_BASKET(-402,"장바구니에 등록되지 않은 상품입니다."),
    PURCHASE_DONE(-403,"이미 구매된 상품입니다."),
    NOT_PURCHASE(-404,"구메하지 않은 상품입니다.");





    private int errorCode;
    private String errorMsg;

    BasketExceptionType(int errorCode, String errorMsg){
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
