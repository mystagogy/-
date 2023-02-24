package com.miniproject.backend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "로그인 후 응답 데이터")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private Long id;
    private String email;
    private String name;
    private String brith;
    private int joinType;
    private String accessToken;
}
