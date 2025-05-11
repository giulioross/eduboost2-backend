package com.example.eduboost_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    private Integer difficulty; // 1-5 scale

    private Double points;

    private String explanation;

    @Column(columnDefinition = "TEXT")
    private String correctAnswer; // For short answer/essay questions

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public enum QuestionType {
        MULTIPLE_CHOICE,
        TRUE_FALSE,
        SHORT_ANSWER,
        ESSAY
    }
}