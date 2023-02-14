package com.miniproject.backend.user.service;


import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signin(UserRequestDTO userRequestDTO) {
        //이메일 중복 확인
        Optional<User> user = userRepository.findByEmail(userRequestDTO.getEmail());

        if(user.isEmpty()){
            User newUser = userRequestDTO.toEntity();
            newUser.encodePassword(passwordEncoder);
            return userRepository.save(newUser);
        }else{
            throw new UserException(UserExceptionType.DUPLICATION_EMAIL);
        }

    }
}
