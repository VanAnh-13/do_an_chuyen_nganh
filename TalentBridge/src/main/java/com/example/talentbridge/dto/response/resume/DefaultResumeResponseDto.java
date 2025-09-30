package com.example.talentbridge.dto.response.resume;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResumeResponseDto {

    private Long id;
    private String email;
    private String jobName;
    private String companyName;
    private String createdAt;
    private String updatedAt;


}
