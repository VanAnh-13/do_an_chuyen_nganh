package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.auth.SessionMetaRequest;
import com.example.talentbridge.dto.response.auth.SessionMetaResponse;
import com.example.talentbridge.model.SessionMeta;

import java.time.Duration;
import java.util.List;

public interface RefreshTokenRedisService {
    void saveRefreshToken(String token, String userId, SessionMetaRequest sessionMetaRequest, Duration expire);

    boolean validateToken(String token, String userId);

    void deleteRefreshToken(String token, String userId);

    void deleteRefreshToken(String key);

    List<SessionMetaResponse> getAllSessionMetas(String userId, String currentRefreshToken);
}
