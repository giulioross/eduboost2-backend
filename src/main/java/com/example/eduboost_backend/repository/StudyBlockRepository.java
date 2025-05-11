package com.example.eduboost_backend.repository;


import com.example.eduboost_backend.model.StudyBlock;
import com.example.eduboost_backend.model.StudyRoutine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface StudyBlockRepository extends JpaRepository<StudyBlock, Long> {

    List<StudyBlock> findByRoutine(StudyRoutine routine);

    List<StudyBlock> findByRoutineAndDayOfWeek(StudyRoutine routine, DayOfWeek dayOfWeek);

    List<StudyBlock> findBySubjectContainingIgnoreCase(String subject);
}