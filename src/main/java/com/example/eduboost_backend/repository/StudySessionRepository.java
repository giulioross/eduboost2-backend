package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.StudyBlock;
import com.example.eduboost_backend.model.StudySession;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudySessionRepository extends JpaRepository<StudySession, Long> {

    List<StudySession> findByUser(User user);

    List<StudySession> findByUserOrderByStartTimeDesc(User user);

    List<StudySession> findByStudyBlock(StudyBlock studyBlock);

    List<StudySession> findByUserAndStartTimeBetween(User user, LocalDateTime start, LocalDateTime end);

    List<StudySession> findByUserAndFocusModeEnabled(User user, boolean focusModeEnabled);
}