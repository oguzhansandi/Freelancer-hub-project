package com.freelance.repository;

import com.freelance.model.RefreshToken;
import com.freelance.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteAllByUser(User user);
}
