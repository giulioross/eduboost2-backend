package com.example.eduboost_backend.dto.quiz;

import com.example.eduboost_backend.model.Quiz;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateQuizRequest {
    @NotBlank
    private String title;

    private String description;

    private String subject;

    private String topic;

    private Quiz.QuizType quizType;

    private boolean adaptive;

    private Integer timeLimit;
}