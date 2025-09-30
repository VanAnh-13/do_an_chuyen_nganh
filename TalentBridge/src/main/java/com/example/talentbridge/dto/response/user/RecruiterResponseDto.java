package com.example.talentbridge.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecruiterResponseDto {
    private Long id;
    private String name;
    private String email;
    private boolean owner;
}
