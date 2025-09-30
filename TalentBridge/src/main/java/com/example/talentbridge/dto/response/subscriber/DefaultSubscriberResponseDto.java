package com.example.talentbridge.dto.response.subscriber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DefaultSubscriberResponseDto {

    private Long id;
    private String email;
    private List<SkillDto> skills;

    public DefaultSubscriberResponseDto(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkillDto {
        private Long id;
        private String name;
    }
}
