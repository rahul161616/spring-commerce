package com.jugger.springcommerce.modules.user.service.impl;

import com.jugger.springcommerce.common.exception.ResourceNotFoundException;
import com.jugger.springcommerce.modules.user.CurrentUserProvider;
import com.jugger.springcommerce.modules.user.dto.UserOwnProfileRequest;
import com.jugger.springcommerce.modules.user.dto.UserOwnProfileResponse;
import com.jugger.springcommerce.modules.user.mapper.UserProfileMapper;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import com.jugger.springcommerce.modules.user.repository.UserProfileRepository;
import com.jugger.springcommerce.modules.user.service.UserPublicService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPublicServiceImpl implements UserPublicService {
    private final CurrentUserProvider currentUserProvider;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserOwnProfileResponse getOwnProfile() {
       return getUserDetailsByIdAndEmail(
               currentUserProvider.getCurrentUserId(),
               currentUserProvider.getCurrentUserEmail()
       );
    }

    private UserOwnProfileResponse getUserDetailsByIdAndEmail(UUID userId, String email) {
        String sql= """
                SELECT full_name,
                        email,
                        phone,
                        image_url,
                        address
                FROM users WHERE id = ? AND email = ?
                """;
        return jdbcTemplate.queryForObject(sql,(rs,rowNum)->userProfileMapper.mapToOwnProfileResponse(rs),userId,email);
    }
    public UserOwnProfileResponse completeMyProfile(UserOwnProfileRequest request){
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        UserProfile userProfile  = userProfileRepository.findById(currentUserProvider.getCurrentUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User profile not found for user id: " + currentUserProvider.getCurrentUserId()));
        if(request.getName()!=null){
            userProfile.setFullName(request.getName());
        }
        if(request.getAddress()!=null){
            userProfile.setAddress(request.getAddress());
        }
        if(request.getPhone()!=null){
            userProfile.setPhone(request.getPhone());
        }
        if(request.getImageUrl()!=null){
            userProfile.setImageUrl(request.getImageUrl());
        }
        UserProfile saved = userProfileRepository.save(userProfile);
        return userProfileMapper.mapToOwnProfileResponse(saved);
    }
}
