package com.miniproject.backend.user.service;

import com.miniproject.backend.user.domain.User;

public interface TokenService {
    public void createRefreshToken(User user, String token);
    public User checkValid(String token);
}
