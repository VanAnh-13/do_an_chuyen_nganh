package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.role.DefaultRoleRequestDto;
import com.example.talentbridge.dto.response.role.DefaultRoleResponseDto;
import com.example.talentbridge.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface RoleService {
    DefaultRoleResponseDto saveRole(DefaultRoleRequestDto defaultRoleRequestDto);

    DefaultRoleResponseDto updateRole(
            Long id,
            DefaultRoleRequestDto defaultRoleRequestDto
    );

    Page<DefaultRoleResponseDto> findAllRoles(
            Specification<Role> spec,
            Pageable pageable);

    DefaultRoleResponseDto deleteRoleById(Long id);
}
