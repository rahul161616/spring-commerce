package com.jugger.springcommerce.modules.user.repository;

import com.jugger.springcommerce.modules.user.model.UserProfile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserProfileRepository extends JpaRepository<UserProfile, UUID> {
    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<UserProfile> findByEmail(String email);
    boolean existsByEmail(String email);
}
