package com.miniproject.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(name = "로그인 DTO", description = "로그인시 입력받는 데이터")
public class LoginRequestDTO {

    @Schema(name = "email", example = "test@email.com")
    private String email;
    @Schema(name = "password", example = "qwer1234")
    private String password;
}
