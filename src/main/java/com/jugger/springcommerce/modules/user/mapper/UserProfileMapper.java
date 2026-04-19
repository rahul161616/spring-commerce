package com.jugger.springcommerce.modules.user.mapper;

import com.jugger.springcommerce.modules.user.dto.UserOwnProfileResponse;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserProfileMapper {

    public UserOwnProfileResponse mapToOwnProfileResponse(ResultSet rs) throws SQLException {
        return UserOwnProfileResponse.builder()
                .name(rs.getString("full_name"))
                .email(rs.getString("email"))
                .phone(rs.getString("phone"))
                .address(rs.getString("address"))
                .imageUrl(rs.getString("image_url"))
                .build();
    }

    public UserOwnProfileResponse mapToOwnProfileResponse(UserProfile userProfile) {
        return UserOwnProfileResponse.builder()
                .name(userProfile.getFullName())
                .email(userProfile.getEmail())
                .phone(userProfile.getPhone())
                .address(userProfile.getAddress())
                .imageUrl(userProfile.getImageUrl())
                .build();
    }
}
