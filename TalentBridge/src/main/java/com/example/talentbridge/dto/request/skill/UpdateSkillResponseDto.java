package com.example.talentbridge.dto.request.skill;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateSkillResponseDto {

    @NotNull(message = "Mã kỹ năng không được để trống")
    private Long id;

    @NotBlank(message = "Tên kỹ năng không được để trống")
    private String name;

}
