package com.example.talentbridge.service;

import com.example.talentbridge.dto.request.skill.CreateSkillRequestDto;
import com.example.talentbridge.dto.request.skill.UpdateSkillResponseDto;
import com.example.talentbridge.dto.response.skill.DefaultSkillResponseDto;
import com.example.talentbridge.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


public interface SkillService {
    DefaultSkillResponseDto saveSkill(CreateSkillRequestDto createSkillRequestDto);

    Page<DefaultSkillResponseDto> findAllSkills(
            Specification<Skill> spec,
            Pageable pageable);

    DefaultSkillResponseDto findSkillById(Long id);

    DefaultSkillResponseDto updateSkillById(UpdateSkillResponseDto updateSkillResponseDto);

    DefaultSkillResponseDto deleteSkillById(Long id);
}
