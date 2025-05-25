package com.example.eduboost_backend.controller;

import com.example.eduboost_backend.model.FocusSession;
import com.example.eduboost_backend.service.FocusSessionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
public class FocusSessionController {

    @Autowired
    private FocusSessionService service;

    @GetMapping("/upcoming-sessions")
    public ResponseEntity<List<FocusSession>> getUpcomingSessions(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<FocusSession> sessions = service.getUpcomingSessions(username);
        return ResponseEntity.ok(sessions);
    }
}
