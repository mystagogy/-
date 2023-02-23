package com.miniproject.backend.user.service.impl;

import com.miniproject.backend.global.jwt.auth.AuthToken;
import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import com.miniproject.backend.user.domain.RefreshToken;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.repository.TokenRepository;
import com.miniproject.backend.user.repository.UserRepository;
import com.miniproject.backend.user.service.TokenService;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {
    private final TokenRepository tokenRepository;
    private final AuthTokenProvider authTokenProvider;
    private final UserService userService;
    private final UserRepository userRepository;

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
        }
        else{
            throw new UserException(UserExceptionType.NOT_EXIST_REFRESH);
        }
        User user = userService.findUserByUserId(email);
        System.out.println(user.getEmail());
        return user;
    }
}
