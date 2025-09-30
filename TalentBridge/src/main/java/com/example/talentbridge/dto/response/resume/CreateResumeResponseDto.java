package com.example.talentbridge.dto.response.resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateResumeResponseDto {

    private Long id;
    private Instant createdAt;
    private String createdBy;

}


