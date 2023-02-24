package com.miniproject.backend.global.config;

import com.miniproject.backend.global.jwt.JwtAuthorizationFilter;
import com.miniproject.backend.global.jwt.JwtExceptionEntryPoint;
import com.miniproject.backend.global.jwt.auth.AuthTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthTokenProvider authTokenProvider;

    // jwt 토큰을 사용하지 않는 URL
    String[] permitUrl = {"/oauth2/**", "/", "/login/**", "/signUp/**", "/product/**", "/refresh", "/swagger-ui/**", "/api-docs/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .cors()//기본 cors 설정
                .and()
                .csrf().disable() //위조요청 방지 비활성화
                .formLogin().disable() //formLogin 인증 비활성화
                .httpBasic().disable() //httpBasic 인증 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(permitUrl)
                .permitAll()
                .anyRequest().authenticated();

        http
                .apply(new customConfig());

        http
                .exceptionHandling()
                .authenticationEntryPoint(new JwtExceptionEntryPoint()); //예외처리

        http
                .logout();

        return http.build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public class customConfig extends AbstractHttpConfigurer<customConfig, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http
                    .addFilterAfter(new JwtAuthorizationFilter(authenticationManager, authTokenProvider), CorsFilter.class);
        }
    }
}
