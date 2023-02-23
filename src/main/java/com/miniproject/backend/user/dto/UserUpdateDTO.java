package com.miniproject.backend.user.dto;

import com.miniproject.backend.user.domain.User;
import lombok.*;

public class UserUpdateDTO {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class update {
       private String name;
        private String password;
        private String birth;
        private int asset;
        private int income;
        private String job;
        private String region;
        private int joinType;

        public User toEntity() {
            return User.builder()
                    .name(name)
                    .password(password)
                    .birth(birth)
                    .asset(asset)
                    .income(income)
                    .job(job)
                    .region(region)
                    .joinType(joinType)
                    .build();
        }
    }
}
