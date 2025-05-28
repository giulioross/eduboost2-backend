package com.example.eduboost_backend.dto.quiz;

import com.example.eduboost_backend.model.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateQuizWithQuestionsRequest {
    @NotBlank
    private String title;

    private String description;

    private String subject;

    private String topic;

    private Question.QuestionType quizType;

    private boolean adaptive;

    private Integer timeLimit;

    @NotNull
    private List<CreateQuestionRequest> questions;
}
