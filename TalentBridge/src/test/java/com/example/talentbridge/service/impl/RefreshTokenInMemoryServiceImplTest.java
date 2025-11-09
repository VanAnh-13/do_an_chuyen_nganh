package com.example.talentbridge.service.impl;

import com.example.talentbridge.dto.request.auth.SessionMetaRequest;
import com.example.talentbridge.dto.response.auth.SessionMetaResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("RefreshTokenInMemoryServiceImpl Unit Tests")
class RefreshTokenInMemoryServiceImplTest {

    private RefreshTokenInMemoryServiceImpl refreshTokenService;

    @BeforeEach
    void setUp() {
        refreshTokenService = new RefreshTokenInMemoryServiceImpl();
    }

    @Test
    @DisplayName("Should save and validate refresh token successfully")
    void testSaveAndValidateToken() {
        // Arrange
        String token = "test-refresh-token-123";
        String userId = "user-001";
        SessionMetaRequest sessionMetaRequest = new SessionMetaRequest(
                "Chrome Browser",
                "Desktop",
                "Mozilla/5.0"
        );
        Duration expire = Duration.ofMinutes(30);

        // Act
        refreshTokenService.saveRefreshToken(token, userId, sessionMetaRequest, expire);
        boolean isValid = refreshTokenService.validateToken(token, userId);

        // Assert
        assertTrue(isValid, "Token should be valid after saving");
    }

    @Test
    @DisplayName("Should return false for non-existent token")
    void testValidateNonExistentToken() {
        // Arrange
        String token = "non-existent-token";
        String userId = "user-001";

        // Act
        boolean isValid = refreshTokenService.validateToken(token, userId);

        // Assert
        assertFalse(isValid, "Non-existent token should be invalid");
    }

    @Test
    @DisplayName("Should delete refresh token successfully")
    void testDeleteRefreshToken() {
        // Arrange
        String token = "test-refresh-token-to-delete";
        String userId = "user-002";
        SessionMetaRequest sessionMetaRequest = new SessionMetaRequest(
                "Safari Browser",
                "Mobile",
                "Safari/537.36"
        );
        Duration expire = Duration.ofMinutes(30);

        // Act
        refreshTokenService.saveRefreshToken(token, userId, sessionMetaRequest, expire);
        assertTrue(refreshTokenService.validateToken(token, userId), "Token should exist before deletion");

        refreshTokenService.deleteRefreshToken(token, userId);
        boolean isValidAfterDelete = refreshTokenService.validateToken(token, userId);

        // Assert
        assertFalse(isValidAfterDelete, "Token should be invalid after deletion");
    }

    @Test
    @DisplayName("Should return all session metas for a user")
    void testGetAllSessionMetas() {
        // Arrange
        String userId = "user-003";
        String token1 = "token-session-1";
        String token2 = "token-session-2";
        String currentToken = token1;

        SessionMetaRequest session1 = new SessionMetaRequest("Chrome", "Desktop", "Chrome/90.0");
        SessionMetaRequest session2 = new SessionMetaRequest("Firefox", "Desktop", "Firefox/88.0");

        // Act
        refreshTokenService.saveRefreshToken(token1, userId, session1, Duration.ofMinutes(30));
        refreshTokenService.saveRefreshToken(token2, userId, session2, Duration.ofMinutes(30));

        List<SessionMetaResponse> sessionMetas = refreshTokenService.getAllSessionMetas(userId, currentToken);

        // Assert
        assertNotNull(sessionMetas, "Session metas list should not be null");
        assertEquals(2, sessionMetas.size(), "Should return 2 session metas");
        
        // Verify current session is marked correctly
        long currentSessionCount = sessionMetas.stream().filter(SessionMetaResponse::isCurrent).count();
        assertEquals(1, currentSessionCount, "Exactly one session should be marked as current");
    }

    @Test
    @DisplayName("Should return empty list when no sessions exist for user")
    void testGetAllSessionMetasEmptyList() {
        // Arrange
        String userId = "user-no-sessions";
        String currentToken = "some-token";

        // Act
        List<SessionMetaResponse> sessionMetas = refreshTokenService.getAllSessionMetas(userId, currentToken);

        // Assert
        assertNotNull(sessionMetas, "Session metas list should not be null");
        assertTrue(sessionMetas.isEmpty(), "Session metas list should be empty");
    }

    @Test
    @DisplayName("Should delete token by session key")
    void testDeleteRefreshTokenByKey() {
        // Arrange
        String token = "test-token-delete-by-key";
        String userId = "user-004";
        SessionMetaRequest sessionMetaRequest = new SessionMetaRequest(
                "Edge Browser",
                "Desktop",
                "Edge/90.0"
        );
        Duration expire = Duration.ofMinutes(30);

        // Act
        refreshTokenService.saveRefreshToken(token, userId, sessionMetaRequest, expire);
        
        // Get all sessions to find the session key
        List<SessionMetaResponse> sessions = refreshTokenService.getAllSessionMetas(userId, token);
        String sessionKey = sessions.get(0).getSessionId();

        refreshTokenService.deleteRefreshToken(sessionKey);
        boolean isValidAfterDelete = refreshTokenService.validateToken(token, userId);

        // Assert
        assertFalse(isValidAfterDelete, "Token should be invalid after deletion by key");
    }

    @Test
    @DisplayName("Should handle multiple users independently")
    void testMultipleUsersIndependently() {
        // Arrange
        String user1Id = "user-100";
        String user2Id = "user-200";
        String token = "same-token-different-users";
        
        SessionMetaRequest session1 = new SessionMetaRequest("Chrome", "Desktop", "Chrome/90.0");
        SessionMetaRequest session2 = new SessionMetaRequest("Firefox", "Mobile", "Firefox/88.0");

        // Act
        refreshTokenService.saveRefreshToken(token, user1Id, session1, Duration.ofMinutes(30));
        refreshTokenService.saveRefreshToken(token, user2Id, session2, Duration.ofMinutes(30));

        boolean user1Valid = refreshTokenService.validateToken(token, user1Id);
        boolean user2Valid = refreshTokenService.validateToken(token, user2Id);

        // Delete user1's token
        refreshTokenService.deleteRefreshToken(token, user1Id);

        boolean user1ValidAfterDelete = refreshTokenService.validateToken(token, user1Id);
        boolean user2ValidAfterDelete = refreshTokenService.validateToken(token, user2Id);

        // Assert
        assertTrue(user1Valid, "User 1 token should be valid initially");
        assertTrue(user2Valid, "User 2 token should be valid initially");
        assertFalse(user1ValidAfterDelete, "User 1 token should be invalid after deletion");
        assertTrue(user2ValidAfterDelete, "User 2 token should still be valid after user 1's deletion");
    }

    @Test
    @DisplayName("Should return correct session metadata")
    void testSessionMetadataContent() {
        // Arrange
        String token = "metadata-test-token";
        String userId = "user-metadata";
        String deviceName = "MacBook Pro";
        String deviceType = "Desktop";
        String userAgent = "Mozilla/5.0 (Macintosh)";

        SessionMetaRequest sessionMetaRequest = new SessionMetaRequest(deviceName, deviceType, userAgent);

        // Act
        refreshTokenService.saveRefreshToken(token, userId, sessionMetaRequest, Duration.ofMinutes(30));
        List<SessionMetaResponse> sessionMetas = refreshTokenService.getAllSessionMetas(userId, token);

        // Assert
        assertFalse(sessionMetas.isEmpty(), "Should have at least one session");
        SessionMetaResponse session = sessionMetas.get(0);
        assertEquals(deviceName, session.getDeviceName(), "Device name should match");
        assertEquals(deviceType, session.getDeviceType(), "Device type should match");
        assertEquals(userAgent, session.getUserAgent(), "User agent should match");
        assertNotNull(session.getLoginAt(), "Login timestamp should not be null");
        assertTrue(session.isCurrent(), "Should be marked as current session");
    }

    @Test
    @DisplayName("Should handle expired tokens correctly")
    void testExpiredTokens() throws InterruptedException {
        // Arrange
        String token = "expiring-token";
        String userId = "user-expire";
        SessionMetaRequest sessionMetaRequest = new SessionMetaRequest(
                "Test Browser",
                "Desktop",
                "TestAgent/1.0"
        );
        Duration expire = Duration.ofMillis(100); // Very short expiration

        // Act
        refreshTokenService.saveRefreshToken(token, userId, sessionMetaRequest, expire);
        assertTrue(refreshTokenService.validateToken(token, userId), "Token should be valid immediately");

        // Wait for expiration
        Thread.sleep(200);

        boolean isValidAfterExpiry = refreshTokenService.validateToken(token, userId);

        // Assert
        assertFalse(isValidAfterExpiry, "Token should be invalid after expiration");
    }
}
