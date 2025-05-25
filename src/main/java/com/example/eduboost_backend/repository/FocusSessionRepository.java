package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.FocusSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FocusSessionRepository extends JpaRepository<FocusSession, Long> {
    List<FocusSession> findByUsernameAndStartTimeAfter(String username, LocalDateTime now);
}
