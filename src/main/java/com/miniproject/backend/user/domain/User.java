package com.miniproject.backend.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(name = "EMAIL_UNIQUE", columnNames = {"email"})})
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String birth; //생년월일+뒷자리 1개

    private int asset; //사용자 자산

    private int income; //소득

    private String job; //직업

    private String region; //지역

    @Column(name = "join_type")
    private int joinType; //가입목적

    @Column(name = "refresh_token")
    private String refreshToken; //jwt 토큰

    @Builder
    public User(String name, String email, String password, String birth, int joinType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.joinType = joinType;
    }

    public void encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
    }

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
