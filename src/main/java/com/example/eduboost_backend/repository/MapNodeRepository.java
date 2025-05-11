package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.MapNode;
import com.example.eduboost_backend.model.MentalMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapNodeRepository extends JpaRepository<MapNode, Long> {

    List<MapNode> findByMentalMap(MentalMap mentalMap);

    List<MapNode> findByMentalMapAndParentNodeIsNull(MentalMap mentalMap);

    List<MapNode> findByParentNode(MapNode parentNode);
}