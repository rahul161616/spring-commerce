package com.jugger.springcommerce.modules.auth.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignUpResponse {
    private String message;
    private String token;
    private String accessToken;
    private String refreshToken;
}
