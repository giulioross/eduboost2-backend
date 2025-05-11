package com.example.eduboost_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "map_nodes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MapNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mental_map_id", nullable = false)
    private MentalMap mentalMap;

    @ManyToOne
    @JoinColumn(name = "parent_node_id")
    private MapNode parentNode;

    @NotBlank
    private String content;

    private String note;

    private Integer xPosition;

    private Integer yPosition;

    private String color;

    @OneToMany(mappedBy = "parentNode", cascade = CascadeType.ALL)
    private List<MapNode> childNodes = new ArrayList<>();
}