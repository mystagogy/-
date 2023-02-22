package com.miniproject.backend.user.service.impl;


import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.repository.UserRepository;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(UserRequestDTO userRequestDTO) {
        //이메일 중복 확인
        Optional<User> user = userRepository.findByEmail(userRequestDTO.getEmail());

        if (user.isEmpty()) {
            User newUser = userRequestDTO.toEntity();
            newUser.encodePassword(passwordEncoder);
            return userRepository.save(newUser);
        } else {
            throw new UserException(UserExceptionType.DUPLICATION_EMAIL);
        }

    }

    @Override
    public Boolean updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );
        System.out.println(user.getPassword());
        user.updatePassword(passwordEncoder, newPassword);
        System.out.println(user.getPassword());
        return true;
    }

    @Override
    public User findUserByUserId(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(!user.isEmpty()){
            return user.get();
        }else{
            throw new UserException(UserExceptionType.ACCOUNT_NOT_EXIST);
        }
    }

    @Override
    public Boolean matchedPasswords(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );

        password = passwordEncoder.encode(password);

        return passwordEncoder.matches(user.getPassword(), password);
    }
}
