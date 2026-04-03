package com.jugger.springcommerce.modules.auth.mapper;

import com.jugger.springcommerce.modules.auth.dto.UserLoginResponse;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class AuthLoginMapper {
    public UserLoginResponse mapToLoginResponse(UserProfile user) {
        return UserLoginResponse.builder()
                .message("Login successful")
                .token(null)
                .accessToken(null)
                .refreshToken(null)
                .build();
    }
}
