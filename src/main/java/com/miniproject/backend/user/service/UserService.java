package com.miniproject.backend.user.service;

import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.dto.UserUpdateDTO;

public interface UserService {
    User signup(UserRequestDTO userRequestDTO);

    User findUserByUserId(String userEmail);

    Boolean matchedPasswords(String email, String password);

    Boolean checkDuplicationEmail(String email);

    Boolean deleteUser(String email);

    Boolean updateUser(String email, UserUpdateDTO.update update);
}
