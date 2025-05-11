package com.example.eduboost_backend.dto.notification;


import com.example.eduboost_backend.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private Notification.NotificationType type;
    private boolean read;
    private LocalDateTime createdAt;
    private LocalDateTime scheduledAt;
    private boolean sent;
    private LocalDateTime sentAt;

    public static NotificationDTO fromEntity(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setRead(notification.isRead());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setScheduledAt(notification.getScheduledAt());
        dto.setSent(notification.isSent());
        dto.setSentAt(notification.getSentAt());
        return dto;
    }
}