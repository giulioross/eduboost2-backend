package com.example.eduboost_backend.service;

import com.example.eduboost_backend.model.FocusSession;
import com.example.eduboost_backend.repository.FocusSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FocusSessionService {
    @Autowired
    private FocusSessionRepository repository;

    public List<FocusSession> getUpcomingSessions(String username) {
        return repository.findByUsernameAndStartTimeAfter(username, LocalDateTime.now());
    }
}
