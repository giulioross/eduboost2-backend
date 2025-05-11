package com.example.eduboost_backend.dto.quiz;

import com.example.eduboost_backend.model.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateQuestionRequest {
    @NotBlank
    private String questionText;

    @NotNull
    private Question.QuestionType questionType;

    private Integer difficulty = 3; // Default medium difficulty

    private Double points = 1.0; // Default 1 point

    private String explanation;

    private String correctAnswer; // For short answer/essay questions

    private List<CreateOptionRequest> options = new ArrayList<>();
}