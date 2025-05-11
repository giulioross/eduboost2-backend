package com.example.eduboost_backend.dto.session;

import com.example.eduboost_backend.model.StudySession;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateStudySessionRequest {
    private Long studyBlockId;

    @NotBlank
    private String subject;

    private String topic;

    private LocalDateTime startTime = LocalDateTime.now();

    private boolean focusModeEnabled;
}