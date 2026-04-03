package com.jugger.springcommerce.modules.auth.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.auth.dto.UserLoginRequest;
import com.jugger.springcommerce.modules.auth.dto.UserLoginResponse;
import com.jugger.springcommerce.modules.auth.dto.UserSignUpRequest;
import com.jugger.springcommerce.modules.auth.dto.UserSignUpResponse;
import com.jugger.springcommerce.modules.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiConstants.AUTH)
@RequiredArgsConstructor
@RestController
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> signUpUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest){

        UserSignUpResponse userSignUpResponse = authService.signUpUser(userSignUpRequest);
        return ResponseEntity.ok().body(userSignUpResponse);

    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        UserLoginResponse userLoginResponse = authService.loginUser(userLoginRequest);
        return ResponseEntity.ok(userLoginResponse);
    }

}
