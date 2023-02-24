package com.miniproject.backend.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String birth; //생년월일+뒷자리 1개
    private int asset; //사용자 자산
    private int income; //소득
    private String job; //직업
    private String region; //지역
    private int joinType; //가입목적
}
