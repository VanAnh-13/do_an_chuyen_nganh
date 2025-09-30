package com.example.talentbridge.dto.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.talentbridge.model.constant.Gender;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequestDto {

    @NotNull(message = "ID Không được để trống")
    private Long id;
    private String name;
    private Gender gender;
    private LocalDate dob;
    private String address;
    private CompanyIdDto company;
    private RoleIdDto role;
}
