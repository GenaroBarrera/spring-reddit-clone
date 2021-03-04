package com.example.springredditclone.service;

import com.example.springredditclone.exceptions.SpringRedditException;
import com.example.springredditclone.model.RefreshToken;
import com.example.springredditclone.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * Class: RefreshTokenService service
 * Implement logic to generate Refresh Tokens.
 * Let’s create a class called RefreshTokenService.java where we can implement the logic to manage our Refresh Tokens.
 */
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * The first method we have is generateRefreshToken() which creates a random 128 bit UUID String and we are using that as our token.
     * We are setting the createdDate as Instant.now(). And then we are saving the token to our MySQL Database.
     * @return
     */
    RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Next, we have validateRefreshToken() which queries the DB with the given token.
     * If the token is not found it throws an Exception with message – “Invalid refresh Token”
     * @param token
     */
    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringRedditException("Invalid refresh Token"));
    }

    /**
     * Lastly, we have deleteRefreshToken() which as name suggests deletes the refresh token from the database.
     * @param token
     */
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}