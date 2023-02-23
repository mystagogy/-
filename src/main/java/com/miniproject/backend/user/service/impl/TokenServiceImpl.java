package com.miniproject.backend.user.service.impl;

import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import com.miniproject.backend.user.domain.RefreshToken;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.repository.TokenRepository;
import com.miniproject.backend.user.repository.UserRepository;
import com.miniproject.backend.user.service.TokenService;
import com.miniproject.backend.user.service.UserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final UserService userService;


    @Override
    public void createRefreshToken(User user, String token) {

        RefreshToken refreshToken = new RefreshToken(user.getEmail(), token);
        tokenRepository.save(refreshToken);

    }

    @Override
    public User checkValid(String token) {
        String email = "";
        if(tokenRepository.existsByToken(token)){
            RefreshToken refreshToken = tokenRepository.findByToken(token);
            email = refreshToken.getEmail();
            User user = userService.findUserByUserId(email);
            return user;
        }
        else
            throw new JwtException("유효하지 않은 refresh token 입니다.");

    }

    @Override
    public void delete(String token) {
        RefreshToken refreshToken = tokenRepository.findByToken(token);
        if(refreshToken!=null){
            tokenRepository.delete(refreshToken);
        }
        else{
            throw new JwtException("유효하지 않은 refresh token 입니다.");
        }

    }
}
