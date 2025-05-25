package com.example.eduboost_backend.dto.map;

import com.example.eduboost_backend.model.MapNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapNodeDTO {
    private Long id;
    private Long parentNodeId;
    private String content;
    private String note;
    private Integer xPosition;
    private Integer yPosition;
    private String color;
    private List<MapNodeDTO> children = new ArrayList<>();

    public static MapNodeDTO fromEntity(MapNode node) {
        MapNodeDTO dto = new MapNodeDTO();
        dto.setId(node.getId());

        if (node.getParentNode() != null) {
            dto.setParentNodeId(node.getParentNode().getId());
        }

        dto.setContent(node.getContent());
        dto.setNote(node.getNote());
        dto.setXPosition(node.getXPosition());
        dto.setYPosition(node.getYPosition());
        dto.setColor(node.getColor());

        if (node.getChildNodes() != null && !node.getChildNodes().isEmpty()) {
            dto.setChildren(node.getChildNodes().stream()
                    .map(MapNodeDTO::fromEntity)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
