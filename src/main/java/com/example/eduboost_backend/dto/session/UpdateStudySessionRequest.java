package com.example.eduboost_backend.dto.session;

import com.example.eduboost_backend.model.StudySession;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UpdateStudySessionRequest {
    private LocalDateTime endTime = LocalDateTime.now();

    private Integer distractionCount = 0;

    private StudySession.ProductivityRating productivityRating;

    private String notes;
}