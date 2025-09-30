package com.example.talentbridge.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import com.example.talentbridge.dto.request.permission.DefaultPermissionRequestDto;
import com.example.talentbridge.dto.response.permission.DefaultPermissionResponseDto;
import com.example.talentbridge.model.Permission;
import com.example.talentbridge.repository.PermissionRepository;
import com.example.talentbridge.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements com.example.talentbridge.service.PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public Page<DefaultPermissionResponseDto> findAllPermission(
            Specification<Permission> spec,
            Pageable pageable
    ) {
        return permissionRepository
                .findAll(spec, pageable)
                .map(this::mapToDefaultResponseDto);
    }


    @Override
    public DefaultPermissionResponseDto savePermission(
            DefaultPermissionRequestDto defaultPermissionRequestDto
    ) {
        Permission permission = new Permission(
                defaultPermissionRequestDto.getName(),
                defaultPermissionRequestDto.getApiPath(),
                defaultPermissionRequestDto.getMethod(),
                defaultPermissionRequestDto.getModule()
        );

        Permission savedPermission = permissionRepository.save(permission);
        return mapToDefaultResponseDto(savedPermission);
    }

    @Override
    public DefaultPermissionResponseDto updatePermission(
            Long id,
            DefaultPermissionRequestDto defaultPermissionRequestDto
    ) {
        Permission permission = permissionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy quyền hạn này"));

        permission.setName(defaultPermissionRequestDto.getName());
        permission.setApiPath(defaultPermissionRequestDto.getApiPath());
        permission.setMethod(defaultPermissionRequestDto.getMethod());
        permission.setModule(defaultPermissionRequestDto.getModule());

        Permission savedPermission = permissionRepository.save(permission);
        return mapToDefaultResponseDto(savedPermission);
    }

    @Override
    public DefaultPermissionResponseDto deletePermission(Long id) {
        Permission permission = permissionRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy quyền hạn này"));

        permission.getRoles().forEach(role -> {
            role.getPermissions().remove(permission);
            roleRepository.saveAndFlush(role);
        });
        
        permissionRepository.delete(permission);
        return mapToDefaultResponseDto(permission);
    }

    private DefaultPermissionResponseDto mapToDefaultResponseDto(Permission permission) {

        DefaultPermissionResponseDto res = new DefaultPermissionResponseDto(
                permission.getId(),
                permission.getName(),
                permission.getApiPath(),
                permission.getMethod(),
                permission.getModule(),
                permission.getCreatedAt().toString(),
                permission.getUpdatedAt().toString()
        );

        return res;
    }

}
