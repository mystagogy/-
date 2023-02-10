package com.miniproject.backend.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class ResponseDTO<T> {
    private int stateCode;
    private boolean success;
    private T data;

    public ResponseDTO(int stateCode, boolean success, T data){
        this.stateCode = stateCode;
        this.success = success;
        this.data = data;
    }

    public ResponseDTO<T> ok(T data) {
        return new ResponseDTO<>(200, true, data);
    }

}
