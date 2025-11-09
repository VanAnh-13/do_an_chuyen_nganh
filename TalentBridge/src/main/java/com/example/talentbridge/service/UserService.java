package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.user.SelfUserUpdatePasswordRequestDto;
import com.example.talentbridge.dto.request.user.SelfUserUpdateProfileRequestDto;
import com.example.talentbridge.dto.request.user.UserCreateRequestDto;
import com.example.talentbridge.dto.request.user.UserUpdateRequestDto;
import com.example.talentbridge.dto.response.user.DefaultUserResponseDto;
import com.example.talentbridge.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    DefaultUserResponseDto saveUser(UserCreateRequestDto userCreateRequestDto);

    Page<DefaultUserResponseDto> findAllUser(Specification<User> spec, Pageable pageable);

    DefaultUserResponseDto findUserById(Long id);

    DefaultUserResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto);

    DefaultUserResponseDto deleteUserById(Long id);

    User findByEmail(String email);

    DefaultUserResponseDto updateSelfUserProfile(SelfUserUpdateProfileRequestDto selfUserUpdateProfileRequestDto);

    DefaultUserResponseDto updateSelfUserPassword(SelfUserUpdatePasswordRequestDto selfUserUpdatePasswordRequestDto);

    void updateSelfUserAvatar(MultipartFile avatarFile);
}
