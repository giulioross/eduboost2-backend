package com.example.eduboost_backend.dto.map;

import com.example.eduboost_backend.model.MentalMap;
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
public class MentalMapDTO {
    private Long id;
    private String title;
    private String description;
    private String mapContent;
    private String subject;
    private String topic;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MapNodeDTO> rootNodes = new ArrayList<>();

    public static MentalMapDTO fromEntity(MentalMap map) {
        MentalMapDTO dto = new MentalMapDTO();
        dto.setId(map.getId());
        dto.setTitle(map.getTitle());
        dto.setDescription(map.getDescription());
        dto.setMapContent(map.getMapContent());
        dto.setSubject(map.getSubject());
        dto.setTopic(map.getTopic());
        dto.setImageUrl(map.getImageUrl());
        dto.setCreatedAt(map.getCreatedAt());
        dto.setUpdatedAt(map.getUpdatedAt());

        if (map.getNodes() != null) {
            dto.setRootNodes(map.getNodes().stream()
                    .filter(node -> node.getParentNode() == null)
                    .map(MapNodeDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
