package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.job.JobRequestDto;
import com.example.talentbridge.dto.response.job.JobResponseDto;
import com.example.talentbridge.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface JobService {
    Page<JobResponseDto> findAllJobs(Specification<Job> spec, Pageable pageable);

    Page<JobResponseDto> findAllJobsForRecruiterCompany(Specification<Job> spec, Pageable pageable);

    JobResponseDto findJobById(Long id);

    JobResponseDto saveJob(JobRequestDto jobRequestDto, boolean isRecruiter);

    JobResponseDto updateJobById(Long id, JobRequestDto jobRequestDto, boolean isRecruiter);

    JobResponseDto deleteJobById(Long id);

    JobResponseDto deleteJobByIdForRecruiterCompany(Long id);

    List<JobResponseDto> findJobByCompanyId(Long id);
}
