package com.example.eduboost_backend.dto.map;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateMentalMapRequest {
    @NotBlank
    private String title;

    private String description;

    private String subject;

    private String topic;
}
