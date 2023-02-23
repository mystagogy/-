package com.miniproject.backend.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.backend.global.dto.ErrorDTO;
import com.miniproject.backend.user.exception.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ObjectMapper om = new ObjectMapper();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        ErrorDTO errorDTO = ErrorDTO.builder()
                .errorCode(UserExceptionType.ACCESS_TOKEN_UN_AUTHORIZED.getErrorCode())
                .errorMessage(UserExceptionType.ACCESS_TOKEN_UN_AUTHORIZED.getMessage())
                .build();
        //to json
        String result = om.writeValueAsString(errorDTO);
        response.setStatus(401);
        response.getWriter().write(result);
    }
}
