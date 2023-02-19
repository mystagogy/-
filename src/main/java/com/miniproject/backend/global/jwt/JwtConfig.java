package com.miniproject.backend.global.jwt;

import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Value("${jwt.secretKey}")
    private String SECRET_KEY;

    @Value("${jwt.access.expiration}")
    private Long ACCESS_EXP;

    @Value("${jwt.refresh.expiration}")
    private Long REFRESH_EXP;

    @Bean
    public AuthTokenProvider jwtProvider() {
        return new AuthTokenProvider(SECRET_KEY);
    }

    public Long getAccessExpiry() {
        return ACCESS_EXP;
    }

    public Long getRefreshExpiry() {
        return REFRESH_EXP;
    }

}
