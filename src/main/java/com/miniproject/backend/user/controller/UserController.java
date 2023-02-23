package com.miniproject.backend.user.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.user.dto.UserUpdateDTO;
import com.miniproject.backend.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @Operation(summary = "비밀번호 확인 API", description = "POST라서 reqeustBody로 입력 필, 변수 명 pw")
    @PostMapping("/users/password")
    public ResponseDTO<?> checkPassword(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Map<String, String> requestMap) {
        boolean result = userService.matchedPasswords(userDetails.getEmail(), requestMap.get("pw"));
        String msg = "";
        if (result) msg = "비밀번호 일치";
        else msg = "비밀번호 불일치";

        return new ResponseDTO<>().ok(result, msg);
    }

    @Operation(summary = "회원 탈퇴 API", description = "회원임을 인증 했다고 가정함")
    @DeleteMapping("/users/me")
    public ResponseDTO<?> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        boolean result = userService.deleteUser(userDetails.getEmail());
        return new ResponseDTO<>().ok(result, "회원 탈퇴 성공");
    }

    @Operation(summary = "회원 정보 변경 API")
    @PutMapping("/users/me")
    public ResponseDTO<?> userUpdate(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserUpdateDTO.update update) {
        boolean result = userService.updateUser(userDetails.getEmail(), update);
        return new ResponseDTO<>().ok(result, "정보 수정 성공");
    }
}
