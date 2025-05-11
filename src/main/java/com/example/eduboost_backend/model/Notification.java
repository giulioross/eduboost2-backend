package com.example.eduboost_backend.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean read;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime scheduledAt;

    private boolean sent;

    private LocalDateTime sentAt;

    public enum NotificationType {
        REMINDER,
        ACHIEVEMENT,
        SYSTEM,
        MOTIVATION,
        OTHER
    }
}