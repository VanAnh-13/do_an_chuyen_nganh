package com.example.talentbridge.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecruiterRequestDto {
    @NotBlank(message = "Email không được để trống")
    private String email;
}
