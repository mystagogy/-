package com.miniproject.backend.user.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CookieProvider;
import com.miniproject.backend.global.jwt.auth.AuthToken;
import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.LoginRequestDTO;
import com.miniproject.backend.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final AuthTokenProvider authTokenProvider;
    private final CookieProvider cookieProvider;

    @PostMapping("/login")
    public ResponseDTO<?> login(@RequestBody LoginRequestDTO requestDTO){

        User user = loginService.login(requestDTO);
        AuthToken authToken = authTokenProvider.issueAccessToken(user);
        AuthToken refreushToken = authTokenProvider.issueRefreshToken(user);
        loginService.updateRefresh(user, refreushToken);
        ResponseCookie tokenCookie = cookieProvider.createRefreshTokenCookie(refreushToken.getToken());

        log.info(String.valueOf(authToken));

        return new ResponseDTO<>().ok(true,"로그인 성공");
    }
}
