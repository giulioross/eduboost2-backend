package com.example.eduboost_backend.dto.routine;


import com.example.eduboost_backend.model.StudyBlock;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class CreateStudyBlockRequest {
    @NotBlank
    private String subject;

    private String topic;

    @NotNull
    private DayOfWeek dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    private StudyBlock.StudyMethod recommendedMethod;

    private Integer breakInterval;

    private Integer breakDuration;
}