package com.miniproject.backend.global.jwt.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.backend.global.exception.GlobalException;
import com.miniproject.backend.global.exception.GlobalExceptionType;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.global.jwt.JwtConfig;
import com.miniproject.backend.global.jwt.JwtType;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.exception.UserException;
import com.miniproject.backend.user.exception.UserExceptionType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class AuthTokenProvider {
    @Autowired
    private JwtConfig jwtConfig;

    private Key key;

    public AuthTokenProvider(String secret){
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public AuthToken createAuthToken(String id, Date expiry) {
        return new AuthToken(key, id, expiry);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public AuthToken issueAccessToken(User user) {
        String subject = getTokenSubjectStr(user, JwtType.ACCESS);
        Date expiryDate = Date.from(
                Instant.now().plusSeconds(jwtConfig.getAccessExpiry()));

        AuthToken authToken = createAuthToken(subject, expiryDate);
        log.info("issueAccessToken.AuthToken.getToken(): {} ", authToken.getToken());
        return authToken;
    }

    public AuthToken issueRefreshToken(User user) {
        String subject = getTokenSubjectStr(user, JwtType.REFRESH);
        Date expiryDate = Date.from(
                Instant.now().plusSeconds(jwtConfig.getRefreshExpiry()));

        AuthToken authToken = createAuthToken(subject, expiryDate);
        log.info("issueRefreshToken.AuthToken.getToken(): {} ", authToken.getToken());
        return authToken;
    }

    /**
     * 토큰 생성 시 호출되는 메서드
     * 토큰 타입에 맞는 CustomUserDetails 객체를 생성해서 String으로 변환해 리턴
     * @param user
     * @param jwtType
     * @return String
     */
    private String getTokenSubjectStr(User user, JwtType jwtType) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(new CustomUserDetails(user.getId(), user.getEmail(), jwtType));
        } catch (JsonProcessingException e) {
            log.debug(e.getMessage());
            throw new UserException(UserExceptionType.PARSING_FAIL);
        }
    }

    /**
     * jwt 검증 후 아이디(이메일) 추출
     * 여기서 JwtException 발생하면 호출한 곳으로 돌아가므로, 호출한 곳(filter)에서는 예외처리 필수
     * @param authToken
     * @return
     */
    public UsernamePasswordAuthenticationToken getAuthentication(AuthToken authToken) throws JsonProcessingException {
        if(authToken.validate()){
            Claims claims = authToken.getClaimsFromToken(); //에러 발생 가능
            CustomUserDetails userDetails = CustomUserDetails.createUserDetails(claims.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, null, AuthorityUtils.NO_AUTHORITIES);
        } else {
            return null;
        }
    }

    /**
     * refresh token 유효성 검사
     * @param token : refresh token
     * @return 유효하면 true, 그 외엔 오류코드 전송
     */
    public boolean validateToken(String token, HttpServletResponse response) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            log.info("Invalid JWT signature.");
            response.setStatus(GlobalExceptionType.BAD_REQUEST.getErrorCode());
            throw new GlobalException(GlobalExceptionType.BAD_REQUEST);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            response.setStatus(GlobalExceptionType.BAD_REQUEST.getErrorCode());
            throw new GlobalException(GlobalExceptionType.BAD_REQUEST);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            response.setStatus(GlobalExceptionType.UNAUTHORIZED.getErrorCode());
            throw new GlobalException(GlobalExceptionType.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            response.setStatus(GlobalExceptionType.JWT_NOT_ALLOWED.getErrorCode());
            throw new GlobalException(GlobalExceptionType.JWT_NOT_ALLOWED);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            response.setStatus(GlobalExceptionType.UNAUTHORIZED.getErrorCode());
            throw new GlobalException(GlobalExceptionType.UNAUTHORIZED);
        }
    }


}
