package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.permission.DefaultPermissionRequestDto;
import com.example.talentbridge.dto.response.permission.DefaultPermissionResponseDto;
import com.example.talentbridge.model.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface PermissionService {
    Page<DefaultPermissionResponseDto> findAllPermission(
            Specification<Permission> spec,
            Pageable pageable
    );

    DefaultPermissionResponseDto savePermission(
            DefaultPermissionRequestDto defaultPermissionRequestDto
    );

    DefaultPermissionResponseDto updatePermission(
            Long id,
            DefaultPermissionRequestDto defaultPermissionRequestDto
    );

    DefaultPermissionResponseDto deletePermission(Long id);
}
