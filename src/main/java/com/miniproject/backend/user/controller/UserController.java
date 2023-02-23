package com.miniproject.backend.user.controller;

import com.miniproject.backend.global.dto.ErrorDTO;
import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @Operation(summary = "비밀번호 확인 API", description = "POST라서 reqeustBody로 입력 필, 변수 명 pw")
    @PostMapping("/users/password")
    public ResponseDTO<?> checkPassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map<String, String> requestMap){
        boolean result = userService.matchedPasswords(userDetails.getEmail(),requestMap.get("pw"));
        String msg = "";
        if(result) msg = "비밀번호 일치";
        else msg = "비밀번호 불일치";

        return new ResponseDTO<>().ok(result, msg);
    }

    @Operation(summary = "비밀번호 변경 API", description = "새로운 비밀번호만 입력, 변수명 newPw")
    @PutMapping("/users/password")
    public ResponseDTO<?> changePassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map<String,String> requestMap){
        boolean result = userService.updatePassword(userDetails.getUsername(),requestMap.get("newPw"));
        //TODO: 로그인으로 돌려보내는 로직 추가하기
        return new ResponseDTO<>().ok(result,"비밀번호 변경 완료");
    }

    @Operation(summary = "회원 탈퇴 API", description = "회원임을 인증 했다고 가정함")
    @DeleteMapping("/users/me")
    public ResponseDTO<?> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails){
        boolean result = userService.deleteUser(userDetails.getEmail());
        return new ResponseDTO<>().ok(result,"회원 탈퇴 성공");
    }
}
