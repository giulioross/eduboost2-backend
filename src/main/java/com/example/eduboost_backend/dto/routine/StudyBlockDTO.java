package com.example.eduboost_backend.dto.routine;


import com.example.eduboost_backend.model.StudyBlock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyBlockDTO {
    private Long id;
    private String subject;
    private String topic;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private StudyBlock.StudyMethod recommendedMethod;
    private Integer breakInterval;
    private Integer breakDuration;

    public static StudyBlockDTO fromEntity(StudyBlock block) {
        StudyBlockDTO dto = new StudyBlockDTO();
        dto.setId(block.getId());
        dto.setSubject(block.getSubject());
        dto.setTopic(block.getTopic());
        dto.setDayOfWeek(block.getDayOfWeek());
        dto.setStartTime(block.getStartTime());
        dto.setEndTime(block.getEndTime());
        dto.setRecommendedMethod(block.getRecommendedMethod());
        dto.setBreakInterval(block.getBreakInterval());
        dto.setBreakDuration(block.getBreakDuration());
        return dto;
    }
}