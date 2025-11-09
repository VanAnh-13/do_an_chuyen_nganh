package com.example.talentbridge.service.impl;

import com.example.talentbridge.dto.request.auth.SessionMetaRequest;
import com.example.talentbridge.dto.response.auth.SessionMetaResponse;
import com.example.talentbridge.model.SessionMeta;
import com.example.talentbridge.service.RefreshTokenRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory implementation of RefreshTokenRedisService for development
 * This is used when Redis is not configured (spring.cache.type != redis)
 */
@Service
@Slf4j
@ConditionalOnProperty(
        name = "spring.cache.type",
        havingValue = "simple",
        matchIfMissing = true
)
public class RefreshTokenInMemoryServiceImpl implements RefreshTokenRedisService {

    // Map to store session metadata by key
    private final Map<String, SessionMeta> sessionStore = new ConcurrentHashMap<>();
    
    // Map to store expiration times
    private final Map<String, Instant> expirationStore = new ConcurrentHashMap<>();

    private String buildKey(String token, String userId) {
        return "auth::refresh_token:" + userId + ":" + DigestUtils.sha256Hex(token);
    }

    @Override
    public void saveRefreshToken(String token, String userId, SessionMetaRequest sessionMetaRequest, Duration expire) {
        String sessionId = buildKey(token, userId);

        SessionMeta sessionMeta = new SessionMeta(
                sessionId,
                sessionMetaRequest.getDeviceName(),
                sessionMetaRequest.getDeviceType(),
                sessionMetaRequest.getUserAgent(),
                Instant.now()
        );

        sessionStore.put(sessionId, sessionMeta);
        expirationStore.put(sessionId, Instant.now().plus(expire));
        
        log.debug("Saved refresh token in memory for user: {}", userId);
        
        // Clean up expired tokens
        cleanupExpiredTokens();
    }

    @Override
    public boolean validateToken(String token, String userId) {
        String key = buildKey(token, userId);
        
        // Check if token exists
        if (!sessionStore.containsKey(key)) {
            return false;
        }
        
        // Check if token is expired
        Instant expiration = expirationStore.get(key);
        if (expiration != null && Instant.now().isAfter(expiration)) {
            // Remove expired token
            sessionStore.remove(key);
            expirationStore.remove(key);
            return false;
        }
        
        return true;
    }

    @Override
    public void deleteRefreshToken(String token, String userId) {
        String key = buildKey(token, userId);
        deleteRefreshToken(key);
    }

    @Override
    public void deleteRefreshToken(String key) {
        sessionStore.remove(key);
        expirationStore.remove(key);
        log.debug("Deleted refresh token: {}", key);
    }

    @Override
    public List<SessionMetaResponse> getAllSessionMetas(String userId, String currentRefreshToken) {
        String keyPrefix = "auth::refresh_token:" + userId + ":";
        String currentTokenHash = DigestUtils.sha256Hex(currentRefreshToken);
        
        cleanupExpiredTokens();
        
        List<SessionMetaResponse> sessionMetas = new ArrayList<>();
        
        for (Map.Entry<String, SessionMeta> entry : sessionStore.entrySet()) {
            String key = entry.getKey();
            
            // Check if key belongs to this user
            if (!key.startsWith(keyPrefix)) {
                continue;
            }
            
            SessionMeta meta = entry.getValue();
            String keyHash = key.substring(key.lastIndexOf(":") + 1);
            boolean isCurrent = currentTokenHash.equals(keyHash);
            
            SessionMetaResponse sessionMetaResponse = new SessionMetaResponse(
                    meta.getSessionId(),
                    meta.getDeviceName(),
                    meta.getDeviceType(),
                    meta.getUserAgent(),
                    meta.getLoginAt(),
                    isCurrent
            );
            sessionMetas.add(sessionMetaResponse);
        }
        
        return sessionMetas;
    }
    
    /**
     * Clean up expired tokens from memory
     */
    private void cleanupExpiredTokens() {
        Instant now = Instant.now();
        List<String> expiredKeys = new ArrayList<>();
        
        for (Map.Entry<String, Instant> entry : expirationStore.entrySet()) {
            if (now.isAfter(entry.getValue())) {
                expiredKeys.add(entry.getKey());
            }
        }
        
        for (String key : expiredKeys) {
            sessionStore.remove(key);
            expirationStore.remove(key);
        }
        
        if (!expiredKeys.isEmpty()) {
            log.debug("Cleaned up {} expired tokens", expiredKeys.size());
        }
    }
}
