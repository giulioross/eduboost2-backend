package com.example.eduboost_backend.controller;


import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.dto.map.CreateMapNodeRequest;
import com.example.eduboost_backend.dto.map.CreateMentalMapRequest;
import com.example.eduboost_backend.dto.map.MapNodeDTO;
import com.example.eduboost_backend.dto.map.MentalMapDTO;
import com.example.eduboost_backend.model.MapNode;
import com.example.eduboost_backend.model.MentalMap;
import com.example.eduboost_backend.service.CloudinaryService;
import com.example.eduboost_backend.service.MentalMapService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/maps")
public class MentalMapController {

    @Autowired
    private MentalMapService mentalMapService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @GetMapping
    public ResponseEntity<List<MentalMapDTO>> getAllMaps() {
        List<MentalMap> maps = mentalMapService.getMapsByUser();
        List<MentalMapDTO> mapDTOs = maps.stream()
                .map(MentalMapDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mapDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentalMapDTO> getMapById(@PathVariable Long id) {
        MentalMap map = mentalMapService.getMapById(id);
        return ResponseEntity.ok(MentalMapDTO.fromEntity(map));
    }

    @PostMapping
    public ResponseEntity<MentalMapDTO> createMap(@Valid @RequestBody CreateMentalMapRequest request) {
        MentalMap map = mentalMapService.createMap(request);
        return ResponseEntity.ok(MentalMapDTO.fromEntity(map));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentalMapDTO> updateMap(@PathVariable Long id, @Valid @RequestBody CreateMentalMapRequest request) {
        MentalMap map = mentalMapService.updateMap(id, request);
        return ResponseEntity.ok(MentalMapDTO.fromEntity(map));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMap(@PathVariable Long id) {
        mentalMapService.deleteMap(id);
        return ResponseEntity.ok(new MessageResponse("Mental map deleted successfully!"));
    }

    @PostMapping("/{mapId}/nodes")
    public ResponseEntity<MapNodeDTO> addNode(@PathVariable Long mapId, @Valid @RequestBody CreateMapNodeRequest request) {
        MapNode node = mentalMapService.addNodeToMap(mapId, request);
        return ResponseEntity.ok(MapNodeDTO.fromEntity(node));
    }

    @PutMapping("/nodes/{nodeId}")
    public ResponseEntity<MapNodeDTO> updateNode(@PathVariable Long nodeId, @Valid @RequestBody CreateMapNodeRequest request) {
        MapNode node = mentalMapService.updateNode(nodeId, request);
        return ResponseEntity.ok(MapNodeDTO.fromEntity(node));
    }

    @DeleteMapping("/nodes/{nodeId}")
    public ResponseEntity<?> deleteNode(@PathVariable Long nodeId) {
        mentalMapService.deleteNode(nodeId);
        return ResponseEntity.ok(new MessageResponse("Map node deleted successfully!"));
    }

    @GetMapping("/{mapId}/nodes/root")
    public ResponseEntity<List<MapNodeDTO>> getRootNodes(@PathVariable Long mapId) {
        List<MapNode> nodes = mentalMapService.getRootNodes(mapId);
        List<MapNodeDTO> nodeDTOs = nodes.stream()
                .map(MapNodeDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nodeDTOs);
    }

    @GetMapping("/nodes/{nodeId}/children")
    public ResponseEntity<List<MapNodeDTO>> getChildNodes(@PathVariable Long nodeId) {
        List<MapNode> nodes = mentalMapService.getChildNodes(nodeId);
        List<MapNodeDTO> nodeDTOs = nodes.stream()
                .map(MapNodeDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(nodeDTOs);
    }

    @PutMapping("/{mapId}/content")
    public ResponseEntity<?> saveMapContent(@PathVariable Long mapId, @RequestBody String mapContent) {
        mentalMapService.saveMapContent(mapId, mapContent);
        return ResponseEntity.ok(new MessageResponse("Map content saved successfully!"));
    }

    @PostMapping("/{mapId}/image")
    public ResponseEntity<?> uploadMapImage(@PathVariable Long mapId, @RequestParam("file") MultipartFile file) throws IOException {
        MentalMap map = mentalMapService.getMapById(mapId);

        String imageUrl = cloudinaryService.uploadFile(file);
        map.setImageUrl(imageUrl);
        mentalMapService.updateMap(mapId, new CreateMentalMapRequest() {{
            setTitle(map.getTitle());
            setDescription(map.getDescription());
            setSubject(map.getSubject());
            setTopic(map.getTopic());
        }});

        return ResponseEntity.ok(new MessageResponse("Map image uploaded successfully!"));
    }
}