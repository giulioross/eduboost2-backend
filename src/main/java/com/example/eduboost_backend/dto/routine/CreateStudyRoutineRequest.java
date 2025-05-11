package com.example.eduboost_backend.dto.routine;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStudyRoutineRequest {
    @NotBlank
    private String name;

    private String description;
}