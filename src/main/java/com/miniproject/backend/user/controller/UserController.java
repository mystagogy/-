package com.miniproject.backend.user.controller;

import com.miniproject.backend.global.dto.ErrorDTO;
import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/signIn")
    public ResponseDTO<?> signin(@RequestBody UserRequestDTO userRequestDTO){
        if(userRequestDTO.getEmail().matches("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
            userService.signin(userRequestDTO);
            return new ResponseDTO<>().ok(true,"회원 생성 성공");
        }else{
            throw new UserException(UserExceptionType.NOT_EMAIL_FORMAT);
        }

    }
}
