package com.example.eduboost_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_responses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    private QuizAttempt attempt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(columnDefinition = "TEXT")
    private String textResponse; // For short answer/essay

    private boolean isCorrect;

    private Double pointsAwarded;

    private Integer timeSpent; // in seconds

    @ManyToMany
    @JoinTable(
            name = "question_response_options",
            joinColumns = @JoinColumn(name = "response_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id")
    )
    private List<Option> selectedOptions = new ArrayList<>();
}