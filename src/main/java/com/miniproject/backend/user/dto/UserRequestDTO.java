package com.miniproject.backend.user.dto;

import com.miniproject.backend.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "회원가입시 입력받을 데이터")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    @Schema(description = "이메일(아이디)", defaultValue = "test@email.com")
    private String email;

    @Schema(description = "비밀번호", defaultValue = "qwer1234")
    private String password;

    @Schema(description = "이름", defaultValue = "김철수")
    private String name;

    @Schema(description = "생년월일-뒷자리1자리", defaultValue = "2301013")
    private String brith;

    @Schema(description = "가입 목적 (숫자로 입력)", defaultValue = "1")
    private int joinType;

    public User toEntity(){
        return User.builder()
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .birth(this.brith)
                .joinType(this.joinType)
                .build();
    }
}
