package com.example.talentbridge.service;

import java.time.Instant;

public interface TokenService {

    String generateToken();

    String hashToken(String token);

    Instant calculateExpiryDate();

    boolean verifyToken(String rawToken, String hashedToken);
}
