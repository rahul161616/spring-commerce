package com.jugger.springcommerce.modules.auth.mapper;

import com.jugger.springcommerce.modules.auth.dto.UserLoginResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthLoginMapper {
    public UserLoginResponse mapToLoginResponse(String accessToken, String refreshToken) {
        return UserLoginResponse.builder()
                .message("Login successful")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
