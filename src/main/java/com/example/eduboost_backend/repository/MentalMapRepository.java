package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.MentalMap;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentalMapRepository extends JpaRepository<MentalMap, Long> {
    List<MentalMap> findByUser(User user);
}
