package com.example.eduboost_backend.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "study_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_block_id")
    private StudyBlock studyBlock;

    private String subject;

    private String topic;

    @CreationTimestamp
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer duration; // in minutes

    private boolean focusModeEnabled;

    private Integer distractionCount;

    @Enumerated(EnumType.STRING)
    private ProductivityRating productivityRating;

    private String notes;

    public enum ProductivityRating {
        VERY_LOW,
        LOW,
        MEDIUM,
        HIGH,
        VERY_HIGH
    }
}