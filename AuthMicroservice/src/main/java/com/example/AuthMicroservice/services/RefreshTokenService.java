package com.example.AuthMicroservice.services;

import com.example.AuthMicroservice.models.RefreshToken;
import com.example.AuthMicroservice.models.User;
import com.example.AuthMicroservice.repositories.RefreshTokenRepository;
import com.example.AuthMicroservice.repositories.UsersRepository;
import com.example.AuthMicroservice.util.TokenRefreshException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UsersRepository userRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User associatedUser = userRepository.findById(userId).get();
        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plusMillis(refreshTokenDurationMs);
        RefreshToken refreshToken = new RefreshToken(associatedUser, token, expiryDate);

        if (userIsPresent(associatedUser)) {
            refreshTokenRepository.setRefreshTokenByUserId(token, expiryDate, userId);
        } else {
            refreshTokenRepository.save(refreshToken);
        }


        return refreshToken;
    }

    public boolean userIsPresent(User user) {
        return refreshTokenRepository.findByUser(user).isPresent();
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }
}
