package com.example.eduboost_backend.dto.routine;

import com.example.eduboost_backend.model.StudyRoutine;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoutineDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<StudyBlockDTO> studyBlocks = new ArrayList<>();

    public static StudyRoutineDTO fromEntity(StudyRoutine routine) {
        StudyRoutineDTO dto = new StudyRoutineDTO();
        dto.setId(routine.getId());
        dto.setName(routine.getName());
        dto.setDescription(routine.getDescription());
        dto.setCreatedAt(routine.getCreatedAt());
        dto.setUpdatedAt(routine.getUpdatedAt());

        if (routine.getStudyBlocks() != null) {
            dto.setStudyBlocks(routine.getStudyBlocks().stream()
                    .map(StudyBlockDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}