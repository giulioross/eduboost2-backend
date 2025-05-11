package com.example.eduboost_backend.service;


import com.example.eduboost_backend.dto.map.CreateMapNodeRequest;
import com.example.eduboost_backend.dto.map.CreateMentalMapRequest;
import com.example.eduboost_backend.model.MapNode;
import com.example.eduboost_backend.model.MentalMap;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.repository.MapNodeRepository;
import com.example.eduboost_backend.repository.MentalMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MentalMapService {

    @Autowired
    private MentalMapRepository mentalMapRepository;

    @Autowired
    private MapNodeRepository mapNodeRepository;

    @Autowired
    private UserService userService;

    public List<MentalMap> getMapsByUser() {
        User currentUser = userService.getCurrentUser();
        return mentalMapRepository.findByUserOrderByUpdatedAtDesc(currentUser);
    }

    public MentalMap getMapById(Long id) {
        User currentUser = userService.getCurrentUser();
        MentalMap map = mentalMapRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mental map not found with id: " + id));

        if (!map.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to access this mental map");
        }

        return map;
    }

    @Transactional
    public MentalMap createMap(CreateMentalMapRequest request) {
        User currentUser = userService.getCurrentUser();

        MentalMap map = new MentalMap();
        map.setUser(currentUser);
        map.setTitle(request.getTitle());
        map.setDescription(request.getDescription());
        map.setSubject(request.getSubject());
        map.setTopic(request.getTopic());

        return mentalMapRepository.save(map);
    }

    @Transactional
    public MentalMap updateMap(Long id, CreateMentalMapRequest request) {
        MentalMap map = getMapById(id);

        map.setTitle(request.getTitle());
        map.setDescription(request.getDescription());
        map.setSubject(request.getSubject());
        map.setTopic(request.getTopic());

        return mentalMapRepository.save(map);
    }

    @Transactional
    public void deleteMap(Long id) {
        MentalMap map = getMapById(id);
        mentalMapRepository.delete(map);
    }

    @Transactional
    public MapNode addNodeToMap(Long mapId, CreateMapNodeRequest request) {
        MentalMap map = getMapById(mapId);

        MapNode node = new MapNode();
        node.setMentalMap(map);
        node.setContent(request.getContent());
        node.setNote(request.getNote());
        node.setXPosition(request.getXPosition());
        node.setYPosition(request.getYPosition());
        node.setColor(request.getColor());

        if (request.getParentNodeId() != null) {
            MapNode parentNode = mapNodeRepository.findById(request.getParentNodeId())
                    .orElseThrow(() -> new RuntimeException("Parent node not found with id: " + request.getParentNodeId()));

            // Check if parent node belongs to the same map
            if (!parentNode.getMentalMap().getId().equals(mapId)) {
                throw new RuntimeException("Parent node does not belong to this mental map");
            }

            node.setParentNode(parentNode);
        }

        return mapNodeRepository.save(node);
    }

    @Transactional
    public MapNode updateNode(Long nodeId, CreateMapNodeRequest request) {
        User currentUser = userService.getCurrentUser();
        MapNode node = mapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Map node not found with id: " + nodeId));

        if (!node.getMentalMap().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to update this node");
        }

        node.setContent(request.getContent());
        node.setNote(request.getNote());
        node.setXPosition(request.getXPosition());
        node.setYPosition(request.getYPosition());
        node.setColor(request.getColor());

        if (request.getParentNodeId() != null) {
            MapNode parentNode = mapNodeRepository.findById(request.getParentNodeId())
                    .orElseThrow(() -> new RuntimeException("Parent node not found with id: " + request.getParentNodeId()));

            // Check if parent node belongs to the same map
            if (!parentNode.getMentalMap().getId().equals(node.getMentalMap().getId())) {
                throw new RuntimeException("Parent node does not belong to this mental map");
            }

            node.setParentNode(parentNode);
        } else {
            node.setParentNode(null);
        }

        return mapNodeRepository.save(node);
    }

    @Transactional
    public void deleteNode(Long nodeId) {
        User currentUser = userService.getCurrentUser();
        MapNode node = mapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Map node not found with id: " + nodeId));

        if (!node.getMentalMap().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to delete this node");
        }

        mapNodeRepository.delete(node);
    }

    public List<MapNode> getRootNodes(Long mapId) {
        MentalMap map = getMapById(mapId);
        return mapNodeRepository.findByMentalMapAndParentNodeIsNull(map);
    }

    public List<MapNode> getChildNodes(Long nodeId) {
        User currentUser = userService.getCurrentUser();
        MapNode parentNode = mapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new RuntimeException("Map node not found with id: " + nodeId));

        if (!parentNode.getMentalMap().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to access this node");
        }

        return mapNodeRepository.findByParentNode(parentNode);
    }

    @Transactional
    public void saveMapContent(Long mapId, String mapContent) {
        MentalMap map = getMapById(mapId);
        map.setMapContent(mapContent);
        mentalMapRepository.save(map);
    }
}