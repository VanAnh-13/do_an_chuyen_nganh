package com.example.talentbridge.dto.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.talentbridge.model.constant.Gender;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultUserResponseDto {

    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private String address;
    private Gender gender;
    private String logoUrl;
    private CompanyInformationDto company;
    private RoleInformationDto role;
    private Instant createdAt;
    private Instant updatedAt;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class CompanyInformationDto {
        private Long id;
        private String name;
        private String address;
        private String logoUrl;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class RoleInformationDto {
        private Long id;
        private String name;
        private String description;
    }


}
