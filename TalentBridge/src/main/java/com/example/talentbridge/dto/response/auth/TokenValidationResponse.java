package com.example.talentbridge.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenValidationResponse {

    private boolean valid;
    private String message;
    private String errorCode;

    public static TokenValidationResponse valid() {
        return TokenValidationResponse.builder()
                .valid(true)
                .message("Token hợp lệ")
                .build();
    }

    public static TokenValidationResponse invalid(String message, String errorCode) {
        return TokenValidationResponse.builder()
                .valid(false)
                .message(message)
                .errorCode(errorCode)
                .build();
    }

    public static TokenValidationResponse expired() {
        return invalid("Liên kết đã hết hạn. Vui lòng yêu cầu liên kết mới", "TOKEN_EXPIRED");
    }

    public static TokenValidationResponse used() {
        return invalid("Liên kết đã được sử dụng", "TOKEN_USED");
    }

    public static TokenValidationResponse notFound() {
        return invalid("Liên kết không hợp lệ", "TOKEN_INVALID");
    }
}
