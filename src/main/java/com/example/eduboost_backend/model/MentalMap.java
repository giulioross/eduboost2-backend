package com.example.eduboost_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mental_maps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MentalMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    private String title;

    private String description;

    @Column(columnDefinition = "TEXT")
    private String mapContent; // JSON structure of the mental map

    private String subject;

    private String topic;

    private String imageUrl; // For storing a rendered image of the map

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "mentalMap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MapNode> nodes = new ArrayList<>();
}