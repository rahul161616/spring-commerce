package com.jugger.springcommerce.modules.user;

import com.jugger.springcommerce.common.exception.BusinessException;
import com.jugger.springcommerce.modules.auth.utils.JwtService;
import com.jugger.springcommerce.modules.user.dto.AuthenticatedUser;
import com.jugger.springcommerce.modules.user.model.UserProfile;
import com.jugger.springcommerce.modules.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserProvider {
    private final UserProfileRepository userProfileRepository;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    public AuthenticatedUser getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof AuthenticatedUser authenticatedUser) {
                return authenticatedUser;
            }

            String email = authentication.getName();
            if (email != null && !email.isBlank() && !"anonymoususer".equalsIgnoreCase(email.trim())) {
                return loadAuthenticatedUserByEmail(email, authentication.getAuthorities());
            }
        }

        String bearerToken = resolveBearerToken();
        if (bearerToken == null || !jwtService.isTokenValid(bearerToken) || !"access".equals(jwtService.extractTokenType(bearerToken))) {
            throw new BusinessException("Invalid authenticated user");
        }

        UUID userId = jwtService.extractUserId(bearerToken);
        String email = jwtService.extractEmail(bearerToken);
        if (email == null || email.isBlank()) {
            throw new BusinessException("Invalid authenticated user");
        }

        return new AuthenticatedUser(
                userId,
                email.trim().toLowerCase(),
                authentication != null ? authentication.getAuthorities() : java.util.List.of()
        );
    }

    public UUID getCurrentUserId() {
        return getCurrentAuthenticatedUser().userId();
    }

    public String getCurrentUserEmail() {
        return getCurrentAuthenticatedUser().email();
    }

    private AuthenticatedUser loadAuthenticatedUserByEmail(String email, java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> authorities) {
        String normalizedEmail = email.trim().toLowerCase();
        UserProfile userProfile = userProfileRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new BusinessException("Invalid authenticated user"));

        return new AuthenticatedUser(
                userProfile.getId(),
                userProfile.getEmail(),
                authorities
        );
    }

    private String resolveBearerToken() {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        return authorizationHeader.substring(7);
    }
}
