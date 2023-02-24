package com.miniproject.backend.user.service.impl;

import com.miniproject.backend.global.exception.GlobalException;
import com.miniproject.backend.global.exception.GlobalExceptionType;

import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import com.miniproject.backend.user.domain.RefreshToken;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.repository.TokenRepository;
import com.miniproject.backend.user.service.TokenService;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;
    private final AuthTokenProvider authTokenProvider;

    /**
     * refresh token 저장
     * @param user : 사용자 정보
     * @param token : refresh token 값
     */
    @Override
    public void createRefreshToken(User user, String token) {

        RefreshToken refreshToken = new RefreshToken(user.getEmail(), token);
        tokenRepository.save(refreshToken);

    }

    /**
     * refresh token으로 사용자 정보 찾기
     * @param token : refresh token
     * @return : 사용자 정보
     */
    @Override
    public User checkValid(String token, HttpServletResponse response) {
        String email = "";
        authTokenProvider.validateToken(token, response);
        if(tokenRepository.existsByToken(token)){
            RefreshToken refreshToken = tokenRepository.findByToken(token);
            email = refreshToken.getEmail();
            User user = userService.findUserByUserId(email);
            return user;
        }
        else {
            throw new GlobalException(GlobalExceptionType.UNAUTHORIZED);
        }
    }

    /**
     * 로그아웃 시 refresh token 테이블에 저장된 토큰 값 삭제
     * @param token : refresh token
     */
    @Override
    public void delete(String token) {
        RefreshToken refreshToken = tokenRepository.findByToken(token);
        if(refreshToken!=null){
            tokenRepository.delete(refreshToken);
        }
        else{
            throw new UserException(UserExceptionType.ACCESS_TOKEN_UN_AUTHORIZED);
        }

    }
}
