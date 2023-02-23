package com.miniproject.backend.user.service.impl;


import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.dto.UserUpdateDTO;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.repository.TokenRepository;
import com.miniproject.backend.user.repository.UserRepository;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(UserRequestDTO userRequestDTO) {
        //이메일 중복 확인
        Optional<User> user = userRepository.findByEmail(userRequestDTO.getEmail());

        if (user.isEmpty()) {
            User newUser = userRequestDTO.toEntity();
            newUser.encodePassword(passwordEncoder);
            return userRepository.save(newUser);
        }

        return null;

    }

    /**
     * 이메일이 DB에 있는지 중복 확인 여부 검사
     * @param email : 확인할 이메일
     * @return true : 중복 아님, false : 중복임
     */
    @Override
    public Boolean checkDuplicationEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        return user.isEmpty();
    }

    @Override
    public User findUserByUserId(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (!user.isEmpty()) {
            return user.get();
        } else {
            throw new UserException(UserExceptionType.ACCOUNT_NOT_EXIST);
        }
    }

    @Override
    public Boolean matchedPasswords(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );

        password = passwordEncoder.encode(password);

        return passwordEncoder.matches(user.getPassword(), password);
    }

    public Boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );
        userRepository.deleteById(user.getId());
        tokenRepository.deleteById(user.getEmail());
        return true;
    }

    @Override
    @Transactional
    public Boolean updateUser(String email, UserUpdateDTO.update update) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST));

        user.updateInfo(
                update.toEntity().getName(),
                passwordEncoder.encode(update.toEntity().getPassword()),
                update.toEntity().getBirth(),
                update.toEntity().getAsset(),
                update.toEntity().getIncome(),
                update.toEntity().getJob(),
                update.toEntity().getRegion(),
                update.toEntity().getJoinType()
        );
        userRepository.save(user);

        return true;

    }
}
