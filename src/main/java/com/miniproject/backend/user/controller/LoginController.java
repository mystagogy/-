package com.miniproject.backend.user.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.auth.AuthToken;
import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.LoginRequestDTO;
import com.miniproject.backend.user.service.LoginService;
import com.miniproject.backend.user.service.TokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final AuthTokenProvider authTokenProvider;
    private final TokenService tokenService;


    @PostMapping("/login")
    public ResponseDTO<?> login(HttpServletResponse response, @RequestBody LoginRequestDTO requestDTO) {

        User user = loginService.login(requestDTO);

        AuthToken authToken = getToken(response, user);

        return new ResponseDTO<>().ok(authToken.getToken(), "로그인 성공");
    }


    @PostMapping("/refresh")
    public ResponseDTO<?> reissue(HttpServletResponse response, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = "";
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("refresh")){
                token = cookie.getValue();
                cookie = new Cookie("refresh", null);
                cookie.setMaxAge(0);

            }
        }
        User user = tokenService.checkValid(token);

        getToken(response, user);

        return null;
    }

    public AuthToken getToken(HttpServletResponse response, User user){
        AuthToken authToken = authTokenProvider.issueAccessToken(user);
        AuthToken refreshToken = authTokenProvider.issueRefreshToken(user);

        Cookie cookie = new Cookie("refresh", refreshToken.getToken());
        tokenService.createRefreshToken(user,refreshToken.getToken());

        response.addCookie(cookie);

        log.info(String.valueOf(authToken.getToken()));

        return authToken;
    }
}
