package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.StudyRoutine;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyRoutineRepository extends JpaRepository<StudyRoutine, Long> {

    List<StudyRoutine> findByUser(User user);

    List<StudyRoutine> findByUserOrderByCreatedAtDesc(User user);
}