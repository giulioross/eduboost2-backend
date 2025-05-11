package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.Notification;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(User user);

    List<Notification> findByUserOrderByCreatedAtDesc(User user);

    List<Notification> findByUserAndRead(User user, boolean read);

    List<Notification> findByUserAndType(User user, Notification.NotificationType type);

    List<Notification> findByScheduledAtBeforeAndSent(LocalDateTime now, boolean sent);
}