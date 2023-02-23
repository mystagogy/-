package com.miniproject.backend.user.service;

import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;

import java.util.Optional;

public interface UserService {
    User signup(UserRequestDTO userRequestDTO);
    User findUserByUserId(String userEmail);
    Boolean updatePassword(String email, String newPassword);
    Boolean matchedPasswords(String email, String password);
    Boolean checkDuplicationEmail(String email);

    Boolean deleteUser(String email);
}
