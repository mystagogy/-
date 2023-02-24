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
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Builder
    public User(String name, String email, String password, String birth, int joinType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.joinType = joinType;
    }

    public void encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String newPw) {
        this.password = passwordEncoder.encode(newPw);
    }

    public void updateInfo(String name, String password, String birth, int asset, int income, String job, String region, int joinType) {
        if(name != null && !name.equals(""))
            this.name = name;
        if(password != null && !password.equals(""))
            this.password = password;
        if(birth != null && !birth.equals(""))
            this.birth = birth;
        if(asset != 0)
            this.asset = asset;
        if(income != 0)
            this.income = income;
        if(job != null && !job.equals(""))
            this.job = job;
        if(region != null && !region.equals(""))
            this.region = region;
        if(joinType != 0)
            this.joinType = joinType;
    }


}
