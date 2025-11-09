package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.resume.ResumeRequestDto;
import com.example.talentbridge.dto.request.resume.UpdateResumeStatusRequestDto;
import com.example.talentbridge.dto.response.resume.CreateResumeResponseDto;
import com.example.talentbridge.dto.response.resume.DefaultResumeResponseDto;
import com.example.talentbridge.dto.response.resume.GetResumeFileResponseDto;
import com.example.talentbridge.dto.response.resume.ResumeForDisplayResponseDto;
import com.example.talentbridge.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    CreateResumeResponseDto saveResume(
            ResumeRequestDto resumeRequestDto,
            MultipartFile pdfFile);

    Page<ResumeForDisplayResponseDto> findAllResumesForRecruiterCompany(
            Specification<Resume> spec,
            Pageable pageable
    );

    Page<ResumeForDisplayResponseDto> findSelfResumes(
            Specification<Resume> spec,
            Pageable pageable);

    DefaultResumeResponseDto removeSelfResumeByJobId(Long jobId);

    DefaultResumeResponseDto updateSelfResumeFile(Long id, MultipartFile pdfFile);

    GetResumeFileResponseDto getResumeFileUrl(Long id);

    Page<ResumeForDisplayResponseDto> findAllResumes(
            Specification<Resume> spec,
            Pageable pageable
    );

    DefaultResumeResponseDto updateResumeStatus(UpdateResumeStatusRequestDto updateResumeStatusRequestDto);

    DefaultResumeResponseDto updateResumeStatusForRecruiterCompany(
            UpdateResumeStatusRequestDto updateResumeStatusRequestDto);
}
