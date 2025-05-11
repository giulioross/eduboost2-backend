package com.example.eduboost_backend.service;

import com.example.eduboost_backend.dto.routine.CreateStudyBlockRequest;
import com.example.eduboost_backend.dto.routine.CreateStudyRoutineRequest;
import com.example.eduboost_backend.model.StudyBlock;
import com.example.eduboost_backend.model.StudyRoutine;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.repository.StudyBlockRepository;
import com.example.eduboost_backend.repository.StudyRoutineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class StudyRoutineService {

    @Autowired
    private StudyRoutineRepository studyRoutineRepository;

    @Autowired
    private StudyBlockRepository studyBlockRepository;

    @Autowired
    private UserService userService;

    public List<StudyRoutine> getRoutinesByUser() {
        User currentUser = userService.getCurrentUser();
        return studyRoutineRepository.findByUserOrderByCreatedAtDesc(currentUser);
    }

    public StudyRoutine getRoutineById(Long id) {
        User currentUser = userService.getCurrentUser();
        StudyRoutine routine = studyRoutineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study routine not found with id: " + id));

        if (!routine.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to access this routine");
        }

        return routine;
    }

    @Transactional
    public StudyRoutine createRoutine(CreateStudyRoutineRequest request) {
        User currentUser = userService.getCurrentUser();

        StudyRoutine routine = new StudyRoutine();
        routine.setUser(currentUser);
        routine.setName(request.getName());
        routine.setDescription(request.getDescription());

        return studyRoutineRepository.save(routine);
    }

    @Transactional
    public StudyRoutine updateRoutine(Long id, CreateStudyRoutineRequest request) {
        StudyRoutine routine = getRoutineById(id);

        routine.setName(request.getName());
        routine.setDescription(request.getDescription());

        return studyRoutineRepository.save(routine);
    }

    @Transactional
    public void deleteRoutine(Long id) {
        StudyRoutine routine = getRoutineById(id);
        studyRoutineRepository.delete(routine);
    }

    @Transactional
    public StudyBlock addBlockToRoutine(Long routineId, CreateStudyBlockRequest request) {
        StudyRoutine routine = getRoutineById(routineId);

        StudyBlock block = new StudyBlock();
        block.setRoutine(routine);
        block.setSubject(request.getSubject());
        block.setTopic(request.getTopic());
        block.setDayOfWeek(request.getDayOfWeek());
        block.setStartTime(request.getStartTime());
        block.setEndTime(request.getEndTime());
        block.setRecommendedMethod(request.getRecommendedMethod());
        block.setBreakInterval(request.getBreakInterval());
        block.setBreakDuration(request.getBreakDuration());

        return studyBlockRepository.save(block);
    }

    @Transactional
    public StudyBlock updateBlock(Long blockId, CreateStudyBlockRequest request) {
        User currentUser = userService.getCurrentUser();
        StudyBlock block = studyBlockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Study block not found with id: " + blockId));

        if (!block.getRoutine().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to update this block");
        }

        block.setSubject(request.getSubject());
        block.setTopic(request.getTopic());
        block.setDayOfWeek(request.getDayOfWeek());
        block.setStartTime(request.getStartTime());
        block.setEndTime(request.getEndTime());
        block.setRecommendedMethod(request.getRecommendedMethod());
        block.setBreakInterval(request.getBreakInterval());
        block.setBreakDuration(request.getBreakDuration());

        return studyBlockRepository.save(block);
    }

    @Transactional
    public void deleteBlock(Long blockId) {
        User currentUser = userService.getCurrentUser();
        StudyBlock block = studyBlockRepository.findById(blockId)
                .orElseThrow(() -> new RuntimeException("Study block not found with id: " + blockId));

        if (!block.getRoutine().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to delete this block");
        }

        studyBlockRepository.delete(block);
    }

    public List<StudyBlock> getBlocksByDay(Long routineId, DayOfWeek dayOfWeek) {
        StudyRoutine routine = getRoutineById(routineId);
        return studyBlockRepository.findByRoutineAndDayOfWeek(routine, dayOfWeek);
    }
}