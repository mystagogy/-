package com.miniproject.backend.user.service.impl;


import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.UserDTO;
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

    /**
     * 회원가입 하는 함수
     * @param userRequestDTO : 회원가입시 필요한 데이터 DTO
     * @return 성공시 user entity 반환, 실패시 null
     */
    @Override
    public User signup(UserRequestDTO userRequestDTO) {
        //이메일 중복 확인
        User user = userRepository.findByEmail(userRequestDTO.getEmail()).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
                );

        User newUser = userRequestDTO.toEntity();
        newUser.encodePassword(passwordEncoder);
        return userRepository.save(newUser);
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

    /**
     * 이메일로 사용자 찾기
     * @param userEmail : 사용자 이메일
     * @return : 사용자 정보
     */
    @Override
    public User findUserByUserId(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (!user.isEmpty()) {
            return user.get();
        } else {
            throw new UserException(UserExceptionType.ACCOUNT_NOT_EXIST);
        }
    }

    /**
     * 비밀번호 확인 함수
     * @param email : 누구의 비밀번호 인지
     * @param password : 어떤 비밀번호를 비교할건지
     * @return 성공시 true 실패시 false
     */
    @Override
    public Boolean matchedPasswords(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );

        password = passwordEncoder.encode(password);

        return passwordEncoder.matches(user.getPassword(), password);
    }

    /**
     * 사용자 삭제
     * @param email : 누구를 삭제할건지
     * @return 성공시 true 실패시 예외처리
     */
    public Boolean deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );
        userRepository.deleteById(user.getId());
        tokenRepository.deleteById(user.getEmail());
        return true;
    }

    /**
     * 사용자 정보 수정
     * @param email : 사용자 email
     * @param update : 사용자 정보를 가지고있는 DTO
     * @return true:사용자 정보 수정
     */
    @Override
    @Transactional
    public Boolean updateUser(String email, UserUpdateDTO.update update) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST));

        String newPw = "";

        if(update.getPassword() != null && !update.getPassword().equals("")){
            newPw = passwordEncoder.encode(update.getPassword());
        }

        user.updateInfo(
                update.toEntity().getName(),
                newPw,
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

    /**
     * 사용자 상세 조회
     * @param email : 누구를 조회할 건지
     * @return UserDTO 반환
     */
    @Override
    public UserDTO selectUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserException(UserExceptionType.ACCOUNT_NOT_EXIST)
        );

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .birth(user.getBirth())
                .asset(user.getAsset())
                .income(user.getIncome())
                .job(user.getJob())
                .region(user.getRegion())
                .joinType(user.getJoinType())
                .build();
    }
}
