package com.example.eduboost_backend.dto.quiz;


import com.example.eduboost_backend.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO {
    private Long id;
    private String questionText;
    private Question.QuestionType questionType;
    private Integer difficulty;
    private Double points;
    private String explanation;
    private String correctAnswer;
    private List<OptionDTO> options = new ArrayList<>();

    public static QuestionDTO fromEntity(Question question) {
        QuestionDTO dto = new QuestionDTO();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setQuestionType(question.getQuestionType());
        dto.setDifficulty(question.getDifficulty());
        dto.setPoints(question.getPoints());
        dto.setExplanation(question.getExplanation());
        dto.setCorrectAnswer(question.getCorrectAnswer());

        if (question.getOptions() != null) {
            dto.setOptions(question.getOptions().stream()
                    .map(OptionDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}