package com.miniproject.backend.loanproduct.exception;

import com.miniproject.backend.global.exception.base.CustomException;
import com.miniproject.backend.global.exception.base.CustomExceptionType;

public class ProductException extends CustomException {

    private ProductExceptionType productExceptionType;

    public ProductException(ProductExceptionType productExceptionType){
        this.productExceptionType = productExceptionType;
    }

    @Override
    public CustomExceptionType getExceptionType() {
        return this.productExceptionType;
    }
}
