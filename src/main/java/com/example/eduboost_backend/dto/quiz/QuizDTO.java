package com.example.eduboost_backend.dto.quiz;

import com.example.eduboost_backend.model.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDTO {
    private Long id;
    private String title;
    private String description;
    private String subject;
    private String topic;
    private Quiz.QuizType quizType;
    private boolean adaptive;
    private Integer timeLimit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<QuestionDTO> questions = new ArrayList<>();

    public static QuizDTO fromEntity(Quiz quiz) {
        QuizDTO dto = new QuizDTO();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        dto.setDescription(quiz.getDescription());
        dto.setSubject(quiz.getSubject());
        dto.setTopic(quiz.getTopic());
        dto.setQuizType(quiz.getQuizType());
        dto.setAdaptive(quiz.isAdaptive());
        dto.setTimeLimit(quiz.getTimeLimit());
        dto.setCreatedAt(quiz.getCreatedAt());
        dto.setUpdatedAt(quiz.getUpdatedAt());

        if (quiz.getQuestions() != null) {
            dto.setQuestions(quiz.getQuestions().stream()
                    .map(QuestionDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}