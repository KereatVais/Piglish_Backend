package com.example.AuthMicroservice.repositories;

import com.example.AuthMicroservice.models.RefreshToken;
import com.example.AuthMicroservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);

    Optional<RefreshToken> findByUser(User user);

    @Modifying
    @Query("update refreshtoken r set r.token = ?1, r.expiryDate = ?2 where r.user.id = ?3")
    void setRefreshTokenByUserId(String token, Instant expiryDate, long userId);
}
