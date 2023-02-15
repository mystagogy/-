package com.miniproject.backend.loanproduct.exception;

import com.miniproject.backend.global.exception.base.CustomExceptionType;

public enum ProductExceptionType implements CustomExceptionType {
    PRODUCT_NOT_EXIST(-201, "상품정보가 존재하지 않습니다.");

    private int errorCode;
    private String errorMsg;

    ProductExceptionType(int errorCode, String errorMsg){
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
