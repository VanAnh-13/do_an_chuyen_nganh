package com.example.talentbridge.service;

import com.example.talentbridge.dto.response.auth.TokenValidationResponse;

public interface PasswordResetService {

    void requestPasswordReset(String email);

    TokenValidationResponse validateResetToken(String token);

    void resetPassword(String token, String newPassword, String confirmPassword);
}
