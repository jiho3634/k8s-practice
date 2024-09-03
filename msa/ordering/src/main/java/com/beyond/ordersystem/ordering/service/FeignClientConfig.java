package com.beyond.ordersystem.ordering.service;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class FeignClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // 현재 인증된 사용자의 정보를 가져옴
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // 인증된 사용자가 JWT 토큰을 가지고 있는지 확인
            if (authentication instanceof JwtAuthenticationToken) {
                JwtAuthenticationToken jwtToken = (JwtAuthenticationToken) authentication;
                // JWT 토큰의 값을 Authorization 헤더에 추가
                String token = jwtToken.getToken().getTokenValue();
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}

