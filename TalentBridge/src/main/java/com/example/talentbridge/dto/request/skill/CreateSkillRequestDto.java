package com.example.talentbridge.dto.request.skill;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSkillRequestDto {

    @NotBlank(message = "Tên kỹ năng không được để trống")
    private String name;
}
