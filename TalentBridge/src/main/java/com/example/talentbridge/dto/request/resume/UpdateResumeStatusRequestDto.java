package com.example.talentbridge.dto.request.resume;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.talentbridge.model.constant.ResumeStatus;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateResumeStatusRequestDto {

    @NotNull(message = "ID người dùng không được để trống")
    private Long id;
    @NotNull(message = "Trạng thái hồ sơ không được để trống")
    private ResumeStatus status;

}
