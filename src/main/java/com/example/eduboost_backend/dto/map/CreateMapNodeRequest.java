package com.example.eduboost_backend.dto.map;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMapNodeRequest {
    private Long parentNodeId;

    @NotBlank
    private String content;

    private String note;

    private Integer xPosition;

    private Integer yPosition;

    private String color;
}
