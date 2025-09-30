package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.company.DefaultCompanyRequestDto;
import com.example.talentbridge.dto.request.user.RecruiterRequestDto;
import com.example.talentbridge.dto.response.company.DefaultCompanyExtendedResponseDto;
import com.example.talentbridge.dto.response.company.DefaultCompanyResponseDto;
import com.example.talentbridge.dto.response.user.RecruiterResponseDto;
import com.example.talentbridge.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


 **/
public interface CompanyService {
    DefaultCompanyResponseDto saveCompany(DefaultCompanyRequestDto dto, MultipartFile logoFile, boolean isRecruiter);

    DefaultCompanyResponseDto updateCompany(DefaultCompanyRequestDto dto, Long id, MultipartFile logoFile, boolean isRecruiter);

    Page<DefaultCompanyResponseDto> findAllCompanies(Specification<Company> spec, Pageable pageable);

    Page<DefaultCompanyExtendedResponseDto> findAllCompaniesWithJobsCount(Specification<Company> spec, Pageable pageable);

    DefaultCompanyResponseDto findCompanyById(Long id);

    DefaultCompanyResponseDto findSelfCompany();

    List<RecruiterResponseDto> findAllRecruitersBySelfCompany();

    void addMemberToCompany(RecruiterRequestDto recruiterRequestDto);

    void removeMemberFromCompany(RecruiterRequestDto recruiterRequestDto);

    DefaultCompanyResponseDto deleteCompanyById(Long id);
}
