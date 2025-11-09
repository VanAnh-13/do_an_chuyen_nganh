package com.example.talentbridge.dto.response.company;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultCompanyResponseDto {

    private Long id;
    private String name;
    private String description;
    private String address;
    private String logoUrl;
    private String createdAt;
    private String updatedAt;

}
