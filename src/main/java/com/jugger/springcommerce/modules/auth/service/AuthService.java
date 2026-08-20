package com.jugger.springcommerce.modules.auth.service;

import com.jugger.springcommerce.modules.auth.dto.TokenRefreshRequest;
import com.jugger.springcommerce.modules.auth.dto.TokenRefreshResponse;
import com.jugger.springcommerce.modules.auth.dto.UserLoginRequest;
import com.jugger.springcommerce.modules.auth.dto.UserLoginResponse;
import com.jugger.springcommerce.modules.auth.dto.UserSignUpRequest;
import com.jugger.springcommerce.modules.auth.dto.UserSignUpResponse;

public interface AuthService {
    UserSignUpResponse signUpUser(UserSignUpRequest userSignUpRequest);
    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);
    TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest);
}
