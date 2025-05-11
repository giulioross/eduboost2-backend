package com.example.eduboost_backend.repository;


import com.example.eduboost_backend.model.MentalMap;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MentalMapRepository extends JpaRepository<MentalMap, Long> {

    List<MentalMap> findByUser(User user);

    List<MentalMap> findByUserOrderByUpdatedAtDesc(User user);

    List<MentalMap> findBySubjectContainingIgnoreCase(String subject);

    List<MentalMap> findByUserAndSubjectContainingIgnoreCase(User user, String subject);
}