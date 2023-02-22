package com.miniproject.backend.global.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CookieProvider {
    private final JwtConfig jwtConfig;

    private String REFRESH_COOKIE = "refreshToken";

    public ResponseCookie createRefreshTokenCookie(String refreshToken){
        deleteRefreshTokenCookie();

        return ResponseCookie.from(REFRESH_COOKIE, refreshToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("None")
                .maxAge(jwtConfig.getRefreshExpiry())
                .path("/refresh")
                .build();
    }

    public void deleteRefreshTokenCookie(){
        ResponseCookie.from(REFRESH_COOKIE,"")
                .maxAge(1)
                .build();
    }
}
