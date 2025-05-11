package com.example.eduboost_backend.service;


import com.example.eduboost_backend.dto.session.CreateStudySessionRequest;
import com.example.eduboost_backend.dto.session.UpdateStudySessionRequest;
import com.example.eduboost_backend.model.StudyBlock;
import com.example.eduboost_backend.model.StudySession;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.repository.StudyBlockRepository;
import com.example.eduboost_backend.repository.StudySessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudySessionService {

    @Autowired
    private StudySessionRepository studySessionRepository;

    @Autowired
    private StudyBlockRepository studyBlockRepository;

    @Autowired
    private UserService userService;

    public List<StudySession> getSessionsByUser() {
        User currentUser = userService.getCurrentUser();
        return studySessionRepository.findByUserOrderByStartTimeDesc(currentUser);
    }

    public StudySession getSessionById(Long id) {
        User currentUser = userService.getCurrentUser();
        StudySession session = studySessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study session not found with id: " + id));

        if (!session.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to access this study session");
        }

        return session;
    }

    @Transactional
    public StudySession startSession(CreateStudySessionRequest request) {
        User currentUser = userService.getCurrentUser();

        StudySession session = new StudySession();
        session.setUser(currentUser);
        session.setSubject(request.getSubject());
        session.setTopic(request.getTopic());
        session.setStartTime(request.getStartTime());
        session.setFocusModeEnabled(request.isFocusModeEnabled());
        session.setDistractionCount(0);

        if (request.getStudyBlockId() != null) {
            StudyBlock block = studyBlockRepository.findById(request.getStudyBlockId())
                    .orElseThrow(() -> new RuntimeException("Study block not found with id: " + request.getStudyBlockId()));

            if (!block.getRoutine().getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("You don't have permission to access this study block");
            }

            session.setStudyBlock(block);
        }

        return studySessionRepository.save(session);
    }

    @Transactional
    public StudySession endSession(Long id, UpdateStudySessionRequest request) {
        StudySession session = getSessionById(id);

        if (session.getEndTime() != null) {
            throw new RuntimeException("This study session has already ended");
        }

        session.setEndTime(request.getEndTime());
        session.setDistractionCount(request.getDistractionCount());
        session.setProductivityRating(request.getProductivityRating());
        session.setNotes(request.getNotes());

        // Calculate duration in minutes
        Duration duration = Duration.between(session.getStartTime(), session.getEndTime());
        session.setDuration((int) duration.toMinutes());

        return studySessionRepository.save(session);
    }

    @Transactional
    public void deleteSession(Long id) {
        StudySession session = getSessionById(id);
        studySessionRepository.delete(session);
    }

    public List<StudySession> getSessionsByDateRange(LocalDateTime start, LocalDateTime end) {
        User currentUser = userService.getCurrentUser();
        return studySessionRepository.findByUserAndStartTimeBetween(currentUser, start, end);
    }

    public List<StudySession> getFocusModeSessionsByUser() {
        User currentUser = userService.getCurrentUser();
        return studySessionRepository.findByUserAndFocusModeEnabled(currentUser, true);
    }
}