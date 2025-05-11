package com.example.eduboost_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "study_blocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "routine_id", nullable = false)
    private StudyRoutine routine;

    @NotBlank
    private String subject;

    private String topic;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private StudyMethod recommendedMethod;

    private Integer breakInterval; // in minutes

    private Integer breakDuration; // in minutes

    public enum StudyMethod {
        POMODORO,
        ACTIVE_RECALL,
        SPACED_REPETITION,
        FEYNMAN_TECHNIQUE,
        MIND_MAPPING,
        NOTE_TAKING,
        OTHER
    }
}