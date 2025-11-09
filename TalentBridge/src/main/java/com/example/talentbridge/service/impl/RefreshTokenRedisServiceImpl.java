package com.example.talentbridge.service.impl;

import lombok.RequiredArgsConstructor;
import com.example.talentbridge.dto.request.auth.SessionMetaRequest;
import com.example.talentbridge.dto.response.auth.SessionMetaResponse;
import com.example.talentbridge.model.SessionMeta;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(
        name = "spring.cache.type",
        havingValue = "redis",
        matchIfMissing = false
)
public class RefreshTokenRedisServiceImpl implements com.example.talentbridge.service.RefreshTokenRedisService {

    private final RedisTemplate<String, SessionMeta> redisSessionMetaTemplate;

    private String buildKey(String token, String userId) {
        return "auth::refresh_token:" + userId + ":" + DigestUtils.sha256Hex(token);
    }

    @Override
    public void saveRefreshToken(
            String token, String userId,
            SessionMetaRequest sessionMetaRequest, Duration expire
    ) {
        String sessionId = buildKey(token, userId);

        SessionMeta sessionMeta = new SessionMeta(
                sessionId,
                sessionMetaRequest.getDeviceName(),
                sessionMetaRequest.getDeviceType(),
                sessionMetaRequest.getUserAgent(),
                Instant.now()
        );

        redisSessionMetaTemplate.opsForValue().set(sessionId, sessionMeta, expire);
    }

    @Override
    public boolean validateToken(String token, String userId) {
        return redisSessionMetaTemplate.hasKey(buildKey(token, userId));
    }

    @Override
    public void deleteRefreshToken(String token, String userId) {
        redisSessionMetaTemplate.delete(buildKey(token, userId));
    }

    @Override
    public void deleteRefreshToken(String key) {
        redisSessionMetaTemplate.delete(key);
    }

    @Override
    public List<SessionMetaResponse> getAllSessionMetas(String userId, String currentRefreshToken) {
        String keyPattern = "auth::refresh_token:" + userId + ":*";
        Set<String> keys = redisSessionMetaTemplate.keys(keyPattern);

        if (keys == null || keys.isEmpty()) return Collections.emptyList();
        String currentTokenHash = DigestUtils.sha256Hex(currentRefreshToken);

        List<SessionMetaResponse> sessionMetas = new ArrayList<>();
        for (String key : keys) {
            SessionMeta meta = redisSessionMetaTemplate.opsForValue().get(key);
            if (meta == null) continue;

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

}
