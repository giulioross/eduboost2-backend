package com.example.eduboost_backend.dto.quiz;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOptionRequest {
    @NotBlank
    private String optionText;

    private boolean isCorrect;
}