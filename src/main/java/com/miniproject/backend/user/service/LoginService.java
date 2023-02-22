package com.miniproject.backend.user.service;

import com.miniproject.backend.global.jwt.auth.AuthToken;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.LoginRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    User login(LoginRequestDTO loginRequestDTO);
    void updateRefresh(User user, AuthToken rt);
}
