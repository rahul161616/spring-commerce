package com.jugger.springcommerce.modules.user.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOwnProfileRequest {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String imageUrl;
}
