package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.auth.UserLoginRequestDto;
import com.example.talentbridge.dto.request.auth.SessionMetaRequest;
import com.example.talentbridge.dto.request.auth.UserRegisterRequestDto;
import com.example.talentbridge.dto.response.auth.AuthResult;
import com.example.talentbridge.dto.response.auth.SessionMetaResponse;
import com.example.talentbridge.dto.response.user.UserDetailsResponseDto;
import com.example.talentbridge.dto.response.user.UserSessionResponseDto;
import org.springframework.http.ResponseCookie;

import java.util.List;


public interface AuthService {

    UserSessionResponseDto handleRegister(UserRegisterRequestDto userRegisterRequestDto);

    AuthResult handleLogin(UserLoginRequestDto userLoginRequestDto);

    ResponseCookie handleLogout(String refreshToken);

    UserDetailsResponseDto getCurrentUserDetails();

    AuthResult handleRefresh(String refreshToken, SessionMetaRequest sessionMetaRequest);

    void removeSelfSession(String sessionId);

    List<SessionMetaResponse> getAllSelfSessionMetas(String refreshToken);

    UserSessionResponseDto getCurrentUser();


}
