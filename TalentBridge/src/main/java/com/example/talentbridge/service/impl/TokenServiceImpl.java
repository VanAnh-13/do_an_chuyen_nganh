package com.example.talentbridge.service.impl;

import com.example.talentbridge.service.TokenService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class TokenServiceImpl implements TokenService {

    private static final int TOKEN_LENGTH = 32;
    private static final int EXPIRY_MINUTES = 15;
    private static final SecureRandom secureRandom = new SecureRandom();

    @Override
    public String generateToken() {
        byte[] randomBytes = new byte[TOKEN_LENGTH];
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }

    @Override
    public String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    @Override
    public Instant calculateExpiryDate() {
        return Instant.now().plus(EXPIRY_MINUTES, ChronoUnit.MINUTES);
    }

    @Override
    public boolean verifyToken(String rawToken, String hashedToken) {
        String computedHash = hashToken(rawToken);
        return computedHash.equals(hashedToken);
    }

    public int getTokenLength() {
        return TOKEN_LENGTH;
    }

    public int getExpiryMinutes() {
        return EXPIRY_MINUTES;
    }
}
