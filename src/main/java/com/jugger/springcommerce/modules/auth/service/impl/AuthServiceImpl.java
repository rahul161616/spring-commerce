package com.jugger.springcommerce.modules.auth.service.impl;

import com.jugger.springcommerce.common.dto.Status;
import com.jugger.springcommerce.common.exception.GeneralRequestInvalidException;
import com.jugger.springcommerce.modules.auth.dto.TokenRefreshRequest;
import com.jugger.springcommerce.modules.auth.dto.TokenRefreshResponse;
import com.jugger.springcommerce.modules.auth.dto.UserLoginRequest;
import com.jugger.springcommerce.modules.auth.dto.UserLoginResponse;
import com.jugger.springcommerce.modules.auth.dto.UserSignUpRequest;
import com.jugger.springcommerce.modules.auth.dto.UserSignUpResponse;
import com.jugger.springcommerce.modules.auth.mapper.AuthLoginMapper;
import com.jugger.springcommerce.modules.auth.mapper.AuthMapper;
import com.jugger.springcommerce.modules.auth.model.RefreshToken;
import com.jugger.springcommerce.modules.auth.repository.RefreshTokenRepository;
import com.jugger.springcommerce.modules.auth.service.AuthService;
import com.jugger.springcommerce.modules.auth.utils.JwtService;
import com.jugger.springcommerce.modules.user.model.Role;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import com.jugger.springcommerce.modules.user.repository.RoleRepository;
import com.jugger.springcommerce.modules.user.repository.UserProfileRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@Log4j2
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthMapper authMapper;
    private final AuthLoginMapper authLoginMapper;
    private final UserProfileRepository userProfileRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserSignUpResponse signUpUser(UserSignUpRequest userSignUpRequest){
        if(userSignUpRequest == null){
            throw new GeneralRequestInvalidException("Sign up request cannot be null");
        }
        if(userSignUpRequest.getName() == null || userSignUpRequest.getName().isBlank()){
            throw new GeneralRequestInvalidException("Name cannot be null or blank");
        }
        if(userSignUpRequest.getEmail() == null || userSignUpRequest.getEmail().isBlank()){
            throw new GeneralRequestInvalidException("Email cannot be null or blank");
        }
        if(userSignUpRequest.getPassword() == null || userSignUpRequest.getPassword().isBlank()){
            throw new GeneralRequestInvalidException("Password cannot be null or blank");
        }

        String normalizedName = userSignUpRequest.getName().trim();
        String normalizedEmail = userSignUpRequest.getEmail().trim().toLowerCase();

        if(userProfileRepository.existsByEmail(normalizedEmail)){
            throw new GeneralRequestInvalidException("User with this email already exists");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new IllegalStateException("Default CUSTOMER role is not configured"));

        UserProfile user = UserProfile.builder()
                .id(UUID.randomUUID())
                .fullName(normalizedName)
                .email(normalizedEmail)
                .password(passwordEncoder.encode(userSignUpRequest.getPassword()))
                .status(Status.ACTIVE)
                .roles(Set.of(customerRole))
                .build();
        UserProfile savedUser = userProfileRepository.save(user);
        log.info("User created successfully with id {}", savedUser.getId());
        return authMapper.mapToSignUpResponse(savedUser);
    }

    @Override
    @Transactional
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest){
        if(userLoginRequest == null){
            throw new GeneralRequestInvalidException("Login request cannot be null");
        }
        if(userLoginRequest.getEmail() == null || userLoginRequest.getEmail().isBlank()){
            throw new GeneralRequestInvalidException("Email cannot be null or blank");
        }
        if(userLoginRequest.getPassword() == null || userLoginRequest.getPassword().isBlank()){
            throw new GeneralRequestInvalidException("Password cannot be null or blank");
        }
        String normalizedEmail = userLoginRequest.getEmail().trim().toLowerCase();

        UserProfile user = userProfileRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new GeneralRequestInvalidException("User with this email does not exist"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new GeneralRequestInvalidException("User account is not active");
        }

        if (!passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new GeneralRequestInvalidException("Invalid email or password");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenRepository.save(buildRefreshTokenEntity(user, refreshToken));

        log.info("User logged in successfully with id {}", user.getId());
        return authLoginMapper.mapToLoginResponse(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public TokenRefreshResponse refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        if (tokenRefreshRequest == null) {
            throw new GeneralRequestInvalidException("Refresh token request cannot be null");
        }
        if (tokenRefreshRequest.getRefreshToken() == null || tokenRefreshRequest.getRefreshToken().isBlank()) {
            throw new GeneralRequestInvalidException("Refresh token cannot be null or blank");
        }

        String rawRefreshToken = tokenRefreshRequest.getRefreshToken().trim();
        if (!jwtService.isTokenValid(rawRefreshToken) || !"refresh".equals(jwtService.extractTokenType(rawRefreshToken))) {
            throw new GeneralRequestInvalidException("Invalid refresh token");
        }

        RefreshToken storedRefreshToken = refreshTokenRepository.findByTokenHash(hashToken(rawRefreshToken))
                .orElseThrow(() -> new GeneralRequestInvalidException("Invalid refresh token"));

        Instant now = Instant.now();
        if (storedRefreshToken.getRevokedAt() != null || storedRefreshToken.getExpiresAt().isBefore(now)) {
            throw new GeneralRequestInvalidException("Refresh token is expired or revoked");
        }

        String email = jwtService.extractEmail(rawRefreshToken);
        UserProfile user = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralRequestInvalidException("Refresh token user does not exist"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new GeneralRequestInvalidException("User account is not active");
        }

        storedRefreshToken.setRevokedAt(now);
        refreshTokenRepository.save(storedRefreshToken);

        String accessToken = jwtService.generateAccessToken(user);
        String nextRefreshToken = jwtService.generateRefreshToken(user);
        refreshTokenRepository.save(buildRefreshTokenEntity(user, nextRefreshToken));

        return TokenRefreshResponse.builder()
                .message("Token refreshed successfully")
                .accessToken(accessToken)
                .refreshToken(nextRefreshToken)
                .build();
    }

    private RefreshToken buildRefreshTokenEntity(UserProfile user, String refreshToken) {
        return RefreshToken.builder()
                .user(user)
                .tokenHash(hashToken(refreshToken))
                .expiresAt(Instant.now().plusMillis(jwtService.getRefreshTokenExpirationMs()))
                .build();
    }

    private String hashToken(String token) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] digest = messageDigest.digest(token.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte value : digest) {
                hex.append(String.format("%02x", value));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("Failed to hash refresh token", exception);
        }
    }

}
