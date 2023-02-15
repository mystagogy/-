package com.miniproject.backend.user.service;

import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;

import java.util.Optional;

public interface UserService {
    User signin(UserRequestDTO userRequestDTO);
    User findUserByUserId(String userEmail);
}
