package com.example.talentbridge.service;

import com.example.talentbridge.service.impl.TokenServiceImpl;
import net.jqwik.api.*;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for TokenService
 */
class TokenServicePropertyTest {

    private final TokenServiceImpl tokenService = new TokenServiceImpl();

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * For any generated reset token, the token SHALL be at least 32 characters long,
     * and the stored value in the database SHALL NOT equal the original token (must be hashed).
     * Validates: Requirements 4.1, 4.5
     */
    @Property(tries = 100)
    void generatedTokensShouldBeAtLeast32Characters() {
        String token = tokenService.generateToken();
        
        assertThat(token)
                .as("Generated token should be at least 32 characters")
                .hasSizeGreaterThanOrEqualTo(32);
    }

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * Hashed token should not equal original token
     */
    @Property(tries = 100)
    void hashedTokenShouldNotEqualOriginalToken() {
        String token = tokenService.generateToken();
        String hashedToken = tokenService.hashToken(token);
        
        assertThat(hashedToken)
                .as("Hashed token should not equal original token")
                .isNotEqualTo(token);
    }

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * Same token should always produce same hash (deterministic)
     */
    @Property(tries = 100)
    void hashingShouldBeDeterministic(
            @ForAll String token
    ) {
        String hash1 = tokenService.hashToken(token);
        String hash2 = tokenService.hashToken(token);
        
        assertThat(hash1).isEqualTo(hash2);
    }

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * Different tokens should produce different hashes (collision resistance)
     */
    @Property(tries = 100)
    void differentTokensShouldProduceDifferentHashes() {
        String token1 = tokenService.generateToken();
        String token2 = tokenService.generateToken();
        
        // Tokens should be different
        assertThat(token1).isNotEqualTo(token2);
        
        // Hashes should be different
        String hash1 = tokenService.hashToken(token1);
        String hash2 = tokenService.hashToken(token2);
        
        assertThat(hash1).isNotEqualTo(hash2);
    }

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * Token verification should work correctly
     */
    @Property(tries = 100)
    void tokenVerificationShouldWork() {
        String token = tokenService.generateToken();
        String hashedToken = tokenService.hashToken(token);
        
        assertThat(tokenService.verifyToken(token, hashedToken))
                .as("Verification should succeed for correct token")
                .isTrue();
    }

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * Wrong token should fail verification
     */
    @Property(tries = 100)
    void wrongTokenShouldFailVerification() {
        String token1 = tokenService.generateToken();
        String token2 = tokenService.generateToken();
        String hashedToken1 = tokenService.hashToken(token1);
        
        assertThat(tokenService.verifyToken(token2, hashedToken1))
                .as("Verification should fail for wrong token")
                .isFalse();
    }

    /**
     * Feature: forgot-password, Property 6: Token expiry consistency
     * For any generated reset token, the expiry time SHALL be exactly 15 minutes
     * (±1 second tolerance) from the creation timestamp.
     * Validates: Requirements 4.2
     */
    @Property(tries = 100)
    void expiryDateShouldBe15MinutesFromNow() {
        Instant before = Instant.now();
        Instant expiryDate = tokenService.calculateExpiryDate();
        Instant after = Instant.now();
        
        // Expected expiry is 15 minutes from now
        Duration expectedDuration = Duration.ofMinutes(15);
        
        // Calculate actual duration
        Duration minDuration = Duration.between(before, expiryDate);
        Duration maxDuration = Duration.between(after, expiryDate);
        
        // Allow 1 second tolerance
        assertThat(minDuration.toSeconds())
                .as("Expiry should be approximately 15 minutes from creation")
                .isBetween(expectedDuration.toSeconds() - 1, expectedDuration.toSeconds() + 1);
    }

    /**
     * Feature: forgot-password, Property 5: Token security properties
     * Generated tokens should be unique (no collisions in reasonable sample)
     */
    @Property(tries = 1)
    void generatedTokensShouldBeUnique() {
        Set<String> tokens = new HashSet<>();
        int sampleSize = 1000;
        
        for (int i = 0; i < sampleSize; i++) {
            String token = tokenService.generateToken();
            assertThat(tokens.add(token))
                    .as("Token should be unique: %s", token)
                    .isTrue();
        }
    }
}
