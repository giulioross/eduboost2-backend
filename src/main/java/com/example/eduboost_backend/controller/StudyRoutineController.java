package com.example.eduboost_backend.controller;

import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.dto.routine.CreateStudyBlockRequest;
import com.example.eduboost_backend.dto.routine.CreateStudyRoutineRequest;
import com.example.eduboost_backend.dto.routine.StudyBlockDTO;
import com.example.eduboost_backend.dto.routine.StudyRoutineDTO;
import com.example.eduboost_backend.model.StudyBlock;
import com.example.eduboost_backend.model.StudyRoutine;
import com.example.eduboost_backend.service.StudyRoutineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/routines")
public class StudyRoutineController {

    @Autowired
    private StudyRoutineService studyRoutineService;

    @GetMapping
    public ResponseEntity<List<StudyRoutineDTO>> getAllRoutines() {
        List<StudyRoutine> routines = studyRoutineService.getRoutinesByUser();
        List<StudyRoutineDTO> routineDTOs = routines.stream()
                .map(StudyRoutineDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(routineDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyRoutineDTO> getRoutineById(@PathVariable Long id) {
        StudyRoutine routine = studyRoutineService.getRoutineById(id);
        return ResponseEntity.ok(StudyRoutineDTO.fromEntity(routine));
    }

    @PostMapping
    public ResponseEntity<StudyRoutineDTO> createRoutine(@Valid @RequestBody CreateStudyRoutineRequest request) {
        StudyRoutine routine = studyRoutineService.createRoutine(request);
        return ResponseEntity.ok(StudyRoutineDTO.fromEntity(routine));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyRoutineDTO> updateRoutine(@PathVariable Long id, @Valid @RequestBody CreateStudyRoutineRequest request) {
        StudyRoutine routine = studyRoutineService.updateRoutine(id, request);
        return ResponseEntity.ok(StudyRoutineDTO.fromEntity(routine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRoutine(@PathVariable Long id) {
        studyRoutineService.deleteRoutine(id);
        return ResponseEntity.ok(new MessageResponse("Study routine deleted successfully!"));
    }

    @PostMapping("/{routineId}/blocks")
    public ResponseEntity<StudyBlockDTO> addBlock(@PathVariable Long routineId, @Valid @RequestBody CreateStudyBlockRequest request) {
        StudyBlock block = studyRoutineService.addBlockToRoutine(routineId, request);
        return ResponseEntity.ok(StudyBlockDTO.fromEntity(block));
    }

    @PutMapping("/blocks/{blockId}")
    public ResponseEntity<StudyBlockDTO> updateBlock(@PathVariable Long blockId, @Valid @RequestBody CreateStudyBlockRequest request) {
        StudyBlock block = studyRoutineService.updateBlock(blockId, request);
        return ResponseEntity.ok(StudyBlockDTO.fromEntity(block));
    }

    @DeleteMapping("/blocks/{blockId}")
    public ResponseEntity<?> deleteBlock(@PathVariable Long blockId) {
        studyRoutineService.deleteBlock(blockId);
        return ResponseEntity.ok(new MessageResponse("Study block deleted successfully!"));
    }

    @GetMapping("/{routineId}/blocks/day/{dayOfWeek}")
    public ResponseEntity<List<StudyBlockDTO>> getBlocksByDay(@PathVariable Long routineId, @PathVariable DayOfWeek dayOfWeek) {
        List<StudyBlock> blocks = studyRoutineService.getBlocksByDay(routineId, dayOfWeek);
        List<StudyBlockDTO> blockDTOs = blocks.stream()
                .map(StudyBlockDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(blockDTOs);
    }
}