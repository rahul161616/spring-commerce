package com.jugger.springcommerce.config;

import com.jugger.springcommerce.modules.user.model.UserProfile;
import com.jugger.springcommerce.modules.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserProfileRepository userProfileRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String normalizedEmail = username == null ? "" : username.trim().toLowerCase();

        UserProfile profile = userProfileRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        return new User(
                profile.getEmail(),
                profile.getPassword(),
                profile.getRoles().stream()
                        .map(role -> {
                            String authority = role.getName().startsWith("ROLE_")
                                    ? role.getName()
                                    : "ROLE_" + role.getName();
                            return new SimpleGrantedAuthority(authority);
                        })
                        .collect(Collectors.toList())
        );
    }
}
