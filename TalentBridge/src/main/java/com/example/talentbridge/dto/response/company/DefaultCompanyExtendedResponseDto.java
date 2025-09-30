package com.example.talentbridge.dto.response.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultCompanyExtendedResponseDto extends DefaultCompanyResponseDto {
    private Long jobsCount;

    public DefaultCompanyExtendedResponseDto(Long id, String name, String description, String address, String logoUrl, String createdAt, String updatedAt, Long jobsCount) {
        super(id, name, description, address, logoUrl, createdAt, updatedAt);
        this.jobsCount = jobsCount;
    }
}
