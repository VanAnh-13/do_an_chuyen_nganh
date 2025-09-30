package com.example.talentbridge.dto.request.role;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultRoleRequestDto {

    @NotBlank(message = "Tên chức vụ không được để trống")
    private String name;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private boolean active;

    @NotNull(message = "Danh sách quyền hạn không được để trống")
    @Valid
    private List<PermissionId> permissions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PermissionId {
        @NotNull(message = "ID quyền hạn không được để trống")
        private Long id;
    }
}
