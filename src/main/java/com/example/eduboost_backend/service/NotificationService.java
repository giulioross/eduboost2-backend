package com.example.eduboost_backend.service;

import com.example.eduboost_backend.dto.notification.CreateNotificationRequest;
import com.example.eduboost_backend.model.Notification;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender emailSender;

    public List<Notification> getNotificationsByUser() {
        User currentUser = userService.getCurrentUser();
        return notificationRepository.findByUserOrderByCreatedAtDesc(currentUser);
    }

    public List<Notification> getUnreadNotificationsByUser() {
        User currentUser = userService.getCurrentUser();
        return notificationRepository.findByUserAndRead(currentUser, false);
    }

    public Notification getNotificationById(Long id) {
        User currentUser = userService.getCurrentUser();
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));

        if (!notification.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to access this notification");
        }

        return notification;
    }

    @Transactional
    public Notification createNotification(CreateNotificationRequest request) {
        User currentUser = userService.getCurrentUser();

        Notification notification = new Notification();
        notification.setUser(currentUser);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setRead(false);
        notification.setScheduledAt(request.getScheduledAt());
        notification.setSent(false);

        return notificationRepository.save(notification);
    }

    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = getNotificationById(id);
        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }

    @Scheduled(fixedRate = 60000) // Run every minute
    @Transactional
    public void sendScheduledNotifications() {
        LocalDateTime now = LocalDateTime.now();
        List<Notification> notificationsToSend = notificationRepository.findByScheduledAtBeforeAndSent(now, false);

        for (Notification notification : notificationsToSend) {
            // Send email notification
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(notification.getUser().getEmail());
                message.setSubject("EduBoost: " + notification.getTitle());
                message.setText(notification.getMessage());
                emailSender.send(message);

                // Update notification as sent
                notification.setSent(true);
                notification.setSentAt(LocalDateTime.now());
                notificationRepository.save(notification);
            } catch (Exception e) {
                // Log error but continue with other notifications
                System.err.println("Failed to send notification: " + e.getMessage());
            }
        }
    }

    @Transactional
    public void createMotivationalNotification(User user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle("Stay Motivated!");
        notification.setMessage("You're doing great with your studies. Keep pushing forward!");
        notification.setType(Notification.NotificationType.MOTIVATION);
        notification.setRead(false);
        notification.setSent(true);
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }
}