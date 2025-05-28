package com.example.eduboost_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quizzes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String title;

    private String description;

    private String subject;

    private String topic;

    @Enumerated(EnumType.STRING)
    private QuizType quizType;

    private boolean adaptive;

    private Integer timeLimit; // in minutes

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    public void setQuizType(Question.QuestionType quizType) {
        if (quizType == Question.QuestionType.MULTIPLE_CHOICE) {
            this.quizType = QuizType.MULTIPLE_CHOICE;
        } else if (quizType == Question.QuestionType.TRUE_FALSE) {
            this.quizType = QuizType.TRUE_FALSE;
        } else if (quizType == Question.QuestionType.SHORT_ANSWER) {
            this.quizType = QuizType.SHORT_ANSWER;
        } else if (quizType == Question.QuestionType.ESSAY) {
            this.quizType = QuizType.ESSAY;
        } else {
            this.quizType = QuizType.MIXED;
        }
    }

    public void setQuizType(QuizType quizType) {
        this.quizType = quizType;
    }

    public enum QuizType {
        MULTIPLE_CHOICE,
        TRUE_FALSE,
        SHORT_ANSWER,
        ESSAY,
        MIXED
    }
}