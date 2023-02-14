package com.miniproject.backend.user.controller;

import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
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
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND")
    })
    @PostMapping("/signIn")
    public boolean signin(@RequestBody UserRequestDTO userRequestDTO){

        return userService.signin(userRequestDTO) != null;
    }
}
