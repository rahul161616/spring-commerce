package com.jugger.springcommerce.modules.auth.repository;

import com.jugger.springcommerce.modules.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findByUserId(UUID userId);

    void deleteByUserId(UUID userId);

    long deleteByExpiresAtBefore(Instant instant);
}
