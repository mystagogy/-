package com.miniproject.backend.user.controller;

import com.miniproject.backend.global.dto.ErrorDTO;
import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.auth.AuthToken;
import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.dto.LoginRequestDTO;
import com.miniproject.backend.user.dto.LoginResponseDTO;
import com.miniproject.backend.user.dto.UserRequestDTO;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import com.miniproject.backend.user.service.LoginService;
import com.miniproject.backend.user.service.TokenService;

import com.miniproject.backend.user.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final AuthTokenProvider authTokenProvider;
    private final TokenService tokenService;
    private final UserServiceImpl userService;

    /**
     * 로그인 기능
     * @param response :
     * @param requestDTO :
     * @return : 로그인 성공시 ture
     */
    @PostMapping("/login")
    public ResponseDTO<?> login(HttpServletResponse response, @RequestBody LoginRequestDTO requestDTO) {

        User user = loginService.login(requestDTO);

        AuthToken authToken = getToken(response, user);

        LoginResponseDTO loginResponseDTO
                = LoginResponseDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .brith(user.getBirth())
                .joinType(user.getJoinType())
                .accessToken(authToken.getToken())
                .build();

        return new ResponseDTO<>().ok(loginResponseDTO, "로그인 성공");
    }

    /**
     * access token 만료 시 refresh token으로 재발행하는 api
     * @param response : 쿠키 값 저장을 위한 응답값
     * @param request : 쿠키 값 확인을 위한 요청 값
     * @return : 새로 발급된 access token 반환
     */
    @PostMapping("/refresh")
    public ResponseDTO<?> reissue(HttpServletResponse response, HttpServletRequest request){
        String token = findRefreshToken(request);
        User user = tokenService.checkValid(token, response);

        AuthToken authToken = getToken(response, user);

        return new ResponseDTO<>().ok(authToken.getToken(), "refresh 성공");
    }

    /**
     * 사용자 정보로 access token과 refresh token 발급 및 쿠키 설정 함수
     * @param response : refresh token을 쿠키에 저장하기 위한 응답값
     * @param user : 토큰 생성을 위한 유저 정보
     * @return access token을 가지고 있는 인증 토큰 객체
     */
    public AuthToken getToken(HttpServletResponse response, User user){
        AuthToken authToken = authTokenProvider.issueAccessToken(user);
        AuthToken refreshToken = authTokenProvider.issueRefreshToken(user);

        Cookie cookie = new Cookie("refresh", refreshToken.getToken());
        tokenService.createRefreshToken(user,refreshToken.getToken());

        response.addCookie(cookie);

        log.info(String.valueOf(authToken.getToken()));

        return authToken;
    }

    /**
     * 쿠키에 저장된 refresh token 값 찾는 함수
     * @param request : 쿠키 값을 받기 위한 요청 값
     * @return refresh token 값
     */
    public String findRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                token = cookie.getValue();
            }
        }
        return token;
    }

    /**
     * 회원가입 api
     * @param userRequestDTO : 회원 가입에 필요한 데이터 DTO
     * @return 성공하면 true
     */
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

    /**
     * 이메일 중복 확인하는 api
     * @param email : 중복 확인할 email
     * @return 중복 아니면 true, 중복이면 false
     */
    @Operation(summary = "이메일 중복 확인 API", description = "true = 중복아님, false = 중복")
    @PostMapping("/signUp/email")
    public ResponseDTO<?> checkEmail(@RequestParam("email") String email){
        if(userService.checkDuplicationEmail(email)){
            return new ResponseDTO<>().ok(true,"사용가능한 이메일 입니다.");
        }else
            return new ResponseDTO<>(401,false,false,"중복된 이메일 입니다.");
    }

    /**
     * refresh token을 제거하고 로그아웃하는 api
     * @param request : 쿠키 값 받아오기 위한 요청 값
     * @param response : 만료시킨 쿠키를 가지고 있는 응답 값
     * @return 로그아웃 완료 메세지
     */
    @Operation(summary = "로그아웃 API")
    @RequestMapping(value = "/user/logout", method = RequestMethod.POST)
    public ResponseDTO<?> logout(HttpServletRequest request, HttpServletResponse response) {

        tokenService.delete(findRefreshToken(request));
        deleteCookie(request,response);
        return new ResponseDTO<>().ok(null, "로그아웃 완료");
    }

    /**
     * 쿠키에 저장된 값 모두 만료하는 함수
     * @param request : 쿠키 값 받아오기 위한 요청 값
     * @param response : 만료시킨 쿠키를 가지고 있는 응답 값
     */
    public void deleteCookie(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0);
                response.addCookie(cookies[i]);
            }
        }
    }
}
