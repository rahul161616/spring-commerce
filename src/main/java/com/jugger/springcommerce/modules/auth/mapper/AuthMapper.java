package com.jugger.springcommerce.modules.auth.mapper;

import com.jugger.springcommerce.modules.auth.dto.UserSignUpResponse;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public UserSignUpResponse mapToSignUpResponse(UserProfile user){
        return UserSignUpResponse.builder()
                .message("User created successfully")
                .accessToken(null)
                .refreshToken(null)
                .token(null)
                .build();
    }
}
