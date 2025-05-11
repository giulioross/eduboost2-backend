package com.example.eduboost_backend.controller;

import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.dto.notification.CreateNotificationRequest;
import com.example.eduboost_backend.dto.notification.NotificationDTO;
import com.example.eduboost_backend.model.Notification;
import com.example.eduboost_backend.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        List<Notification> notifications = notificationService.getNotificationsByUser();
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationDTOs);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        List<Notification> notifications = notificationService.getUnreadNotificationsByUser();
        List<NotificationDTO> notificationDTOs = notifications.stream()
                .map(NotificationDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificationDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(NotificationDTO.fromEntity(notification));
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        Notification notification = notificationService.createNotification(request);
        return ResponseEntity.ok(NotificationDTO.fromEntity(notification));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        return ResponseEntity.ok(NotificationDTO.fromEntity(notification));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok(new MessageResponse("Notification deleted successfully!"));
    }
}