package com.example.eduboost_backend.controller;


import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.dto.session.CreateStudySessionRequest;
import com.example.eduboost_backend.dto.session.StudySessionDTO;
import com.example.eduboost_backend.dto.session.UpdateStudySessionRequest;
import com.example.eduboost_backend.model.StudySession;
import com.example.eduboost_backend.service.StudySessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/sessions")
public class StudySessionController {

    @Autowired
    private StudySessionService studySessionService;

    @GetMapping
    public ResponseEntity<List<StudySessionDTO>> getAllSessions() {
        List<StudySession> sessions = studySessionService.getSessionsByUser();
        List<StudySessionDTO> sessionDTOs = sessions.stream()
                .map(StudySessionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudySessionDTO> getSessionById(@PathVariable Long id) {
        StudySession session = studySessionService.getSessionById(id);
        return ResponseEntity.ok(StudySessionDTO.fromEntity(session));
    }

    @PostMapping("/start")
    public ResponseEntity<StudySessionDTO> startSession(@Valid @RequestBody CreateStudySessionRequest request) {
        StudySession session = studySessionService.startSession(request);
        return ResponseEntity.ok(StudySessionDTO.fromEntity(session));
    }

    @PutMapping("/{id}/end")
    public ResponseEntity<StudySessionDTO> endSession(@PathVariable Long id, @Valid @RequestBody UpdateStudySessionRequest request) {
        StudySession session = studySessionService.endSession(id, request);
        return ResponseEntity.ok(StudySessionDTO.fromEntity(session));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
        studySessionService.deleteSession(id);
        return ResponseEntity.ok(new MessageResponse("Study session deleted successfully!"));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<StudySessionDTO>> getSessionsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);

        List<StudySession> sessions = studySessionService.getSessionsByDateRange(start, end);
        List<StudySessionDTO> sessionDTOs = sessions.stream()
                .map(StudySessionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionDTOs);
    }

    @GetMapping("/focus-mode")
    public ResponseEntity<List<StudySessionDTO>> getFocusModeSessionsByUser() {
        List<StudySession> sessions = studySessionService.getFocusModeSessionsByUser();
        List<StudySessionDTO> sessionDTOs = sessions.stream()
                .map(StudySessionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionDTOs);
    }
}