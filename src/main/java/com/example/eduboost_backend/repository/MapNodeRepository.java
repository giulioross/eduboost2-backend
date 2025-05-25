package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.MapNode;
import com.example.eduboost_backend.model.MentalMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapNodeRepository extends JpaRepository<MapNode, Long> {
    List<MapNode> findByMentalMapAndParentNodeIsNull(MentalMap map);
    List<MapNode> findByParentNode(MapNode parent);
}
