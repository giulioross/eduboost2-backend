package com.example.eduboost_backend.dto.notification;

import com.example.eduboost_backend.model.Notification;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateNotificationRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String message;

    @NotNull
    private Notification.NotificationType type;

    private LocalDateTime scheduledAt;
}