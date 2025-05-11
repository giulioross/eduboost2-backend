package com.example.eduboost_backend.dto.session;


import com.example.eduboost_backend.model.StudySession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudySessionDTO {
    private Long id;
    private Long studyBlockId;
    private String subject;
    private String topic;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    private boolean focusModeEnabled;
    private Integer distractionCount;
    private StudySession.ProductivityRating productivityRating;
    private String notes;

    public static StudySessionDTO fromEntity(StudySession session) {
        StudySessionDTO dto = new StudySessionDTO();
        dto.setId(session.getId());

        if (session.getStudyBlock() != null) {
            dto.setStudyBlockId(session.getStudyBlock().getId());
        }

        dto.setSubject(session.getSubject());
        dto.setTopic(session.getTopic());
        dto.setStartTime(session.getStartTime());
        dto.setEndTime(session.getEndTime());
        dto.setDuration(session.getDuration());
        dto.setFocusModeEnabled(session.isFocusModeEnabled());
        dto.setDistractionCount(session.getDistractionCount());
        dto.setProductivityRating(session.getProductivityRating());
        dto.setNotes(session.getNotes());

        return dto;
    }
}