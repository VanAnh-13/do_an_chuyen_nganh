package com.example.talentbridge.dto.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionMetaRequest {

    private String deviceName;
    private String deviceType;
    private String userAgent;

}
