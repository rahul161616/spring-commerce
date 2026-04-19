package com.jugger.springcommerce.modules.user.controller;

import com.jugger.springcommerce.apiConstants.ApiConstants;
import com.jugger.springcommerce.modules.user.dto.UserOwnProfileRequest;
import com.jugger.springcommerce.modules.user.dto.UserOwnProfileResponse;
import com.jugger.springcommerce.modules.user.service.UserPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstants.USER_PROFILE)
@RequiredArgsConstructor
public class UserPublicController {
    private final UserPublicService userPublicService;

    @GetMapping("/me")
    public ResponseEntity<UserOwnProfileResponse> getOwnProfile(){
        UserOwnProfileResponse userOwnProfileResponse = userPublicService.getOwnProfile();
        return ResponseEntity.ok().body(userOwnProfileResponse);
    }
    @PatchMapping("/me")
    public ResponseEntity<UserOwnProfileResponse> completeMyProfile(@RequestBody UserOwnProfileRequest userOwnProfileRequest){
        UserOwnProfileResponse userOwnProfileResponse = userPublicService.completeMyProfile(userOwnProfileRequest);
        return ResponseEntity.ok().body(userOwnProfileResponse);
    }
}
