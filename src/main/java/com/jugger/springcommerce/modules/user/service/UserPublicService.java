package com.jugger.springcommerce.modules.user.service;

import com.jugger.springcommerce.modules.user.dto.UserOwnProfileRequest;
import com.jugger.springcommerce.modules.user.dto.UserOwnProfileResponse;

public interface UserPublicService {
    UserOwnProfileResponse getOwnProfile();
    UserOwnProfileResponse completeMyProfile(UserOwnProfileRequest userOwnProfileRequest);
}
