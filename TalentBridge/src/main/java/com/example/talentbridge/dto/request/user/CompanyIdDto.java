package com.example.talentbridge.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompanyIdDto {
    @NotNull(message = "Công ty không được để trống")
    private Long id;
}
