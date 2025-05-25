package com.example.eduboost_backend.service;

import com.example.eduboost_backend.dto.map.CreateMapNodeRequest;
import com.example.eduboost_backend.dto.map.CreateMentalMapRequest;
import com.example.eduboost_backend.exeption.ResourceNotFoundException;
import com.example.eduboost_backend.exeption.UserDetailsImpl;
import com.example.eduboost_backend.model.MapNode;
import com.example.eduboost_backend.model.MentalMap;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.repository.MapNodeRepository;
import com.example.eduboost_backend.repository.MentalMapRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MentalMapService {

    private final MentalMapRepository mentalMapRepository;
    private final MapNodeRepository mapNodeRepository;
    private final UserService userService;

    // Iniezione attraverso il costruttore
    public MentalMapService(
            final MentalMapRepository mentalMapRepository,
            final MapNodeRepository mapNodeRepository,
            final UserService userService
    ) {
        this.mentalMapRepository = mentalMapRepository;
        this.mapNodeRepository = mapNodeRepository;
        this.userService = userService;
    }

    // ======================== METODI RELATIVI ALLA MENTAL MAP ========================

    // Recupera tutte le mappe mentali dell'utente autenticato
    public List<MentalMap> getMapsByUser() {
        User user = getAuthenticatedUser();
        return mentalMapRepository.findByUser(user);
    }

    // Recupera una mappa mentale per il suo ID
    public MentalMap getMapById(final Long id) {
        return mentalMapRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mental map not found with id " + id));
    }

    // Crea una nuova mappa mentale per l'utente autenticato
    public MentalMap createMap(final CreateMentalMapRequest request) {
        User user = getAuthenticatedUser();
        MentalMap map = new MentalMap();
        map.setTitle(request.getTitle());
        map.setDescription(request.getDescription());
        map.setSubject(request.getSubject());
        map.setTopic(request.getTopic());
        map.setUser(user);

        return mentalMapRepository.save(map);
    }

    // Aggiorna una mappa mentale esistente
    public MentalMap updateMap(final Long id, final CreateMentalMapRequest request) {
        MentalMap map = getMapById(id);
        map.setTitle(request.getTitle());
        map.setDescription(request.getDescription());
        map.setSubject(request.getSubject());
        map.setTopic(request.getTopic());

        return mentalMapRepository.save(map);
    }

    // Elimina una mappa mentale esistente
    public void deleteMap(final Long id) {
        MentalMap map = getMapById(id);
        mentalMapRepository.delete(map);
    }

    // Salva il contenuto della mappa mentale (aggiornamento del campo 'mapContent')
    public void saveMapContent(final Long mapId, final String mapContent) {
        MentalMap map = getMapById(mapId);
        map.setMapContent(mapContent);
        mentalMapRepository.save(map);
    }

    // ======================== METODI RELATIVI AI NODI ========================

    // Aggiunge un nuovo nodo alla mappa mentale
    public MapNode addNodeToMap(final Long mapId, final CreateMapNodeRequest request) {
        MentalMap map = getMapById(mapId);

        MapNode node = new MapNode();
        node.setMentalMap(map);
        node.setContent(request.getContent());
        node.setNote(request.getNote());
        node.setXPosition(request.getXPosition());
        node.setYPosition(request.getYPosition());
        node.setColor(request.getColor());

        if (request.getParentNodeId() != null) {
            MapNode parent = mapNodeRepository.findById(request.getParentNodeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent node not found with id " + request.getParentNodeId()));

            node.setParentNode(parent);
            parent.getChildNodes().add(node);
            mapNodeRepository.save(parent);
        }

        return mapNodeRepository.save(node);
    }

    // Aggiorna un nodo esistente
    public MapNode updateNode(final Long nodeId, final CreateMapNodeRequest request) {
        MapNode node = mapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Map node not found with id " + nodeId));

        node.setContent(request.getContent());
        node.setNote(request.getNote());
        node.setXPosition(request.getXPosition());
        node.setYPosition(request.getYPosition());
        node.setColor(request.getColor());

        return mapNodeRepository.save(node);
    }

    // Elimina un nodo esistente
    public void deleteNode(final Long nodeId) {
        MapNode node = mapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Map node not found with id " + nodeId));
        mapNodeRepository.delete(node);
    }

    // Recupera tutti i nodi radice per una mappa mentale
    public List<MapNode> getRootNodes(final Long mapId) {
        MentalMap map = getMapById(mapId);
        return mapNodeRepository.findByMentalMapAndParentNodeIsNull(map);
    }

    // Recupera tutti i nodi figli di un nodo specifico
    public List<MapNode> getChildNodes(final Long nodeId) {
        MapNode parent = mapNodeRepository.findById(nodeId)
                .orElseThrow(() -> new ResourceNotFoundException("Map node not found with id " + nodeId));
        return mapNodeRepository.findByParentNode(parent);
    }

    // ======================== METODI UTILI ========================

    // Recupera l'utente attualmente autenticato
    private User getAuthenticatedUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserById(userDetails.getId());
    }
}
