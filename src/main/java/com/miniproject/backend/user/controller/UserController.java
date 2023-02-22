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

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "회원가입 api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 생성 성공"),
            @ApiResponse(responseCode = "-101", description = "이메일 중복",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "-102", description = "이메일 형식",
                    content = @Content(schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping("/signUp")
    public ResponseDTO<?> signup(@RequestBody UserRequestDTO userRequestDTO){
        if(userRequestDTO.getEmail().matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
            userService.signup(userRequestDTO);
            return new ResponseDTO<>().ok(true,"회원 생성 성공");
        }else{
            throw new UserException(UserExceptionType.NOT_EMAIL_FORMAT);
        }

    }

    @Operation(summary = "이메일 중복 확인 API", description = "true = 중복아님, false = 중복")
    @PostMapping("/signUp/email")
    public ResponseDTO<?> checkEmail(@RequestParam("email") String email){
        if(userService.checkDuplicationEmail(email)){
            return new ResponseDTO<>().ok(true,"사용가능한 이메일 입니다.");
        }else
            return new ResponseDTO<>(401,false,false,"중복된 이메일 입니다.");
    }

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
}
