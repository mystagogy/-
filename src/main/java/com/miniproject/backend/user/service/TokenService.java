package com.miniproject.backend.user.service;

import com.miniproject.backend.user.domain.User;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

public interface TokenService {
    public void createRefreshToken(User user, String token);
    public User checkValid(String token, HttpServletResponse response);
    public void delete(String token);
}
