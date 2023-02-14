package com.miniproject.backend.user.service;

import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;

public interface UserService {
    User signin(UserRequestDTO userRequestDTO);
}
