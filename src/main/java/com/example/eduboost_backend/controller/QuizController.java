package com.example.eduboost_backend.controller;


import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.dto.quiz.*;
import com.example.eduboost_backend.model.Question;
import com.example.eduboost_backend.model.Quiz;
import com.example.eduboost_backend.service.QuizService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getUserQuizzes() {
        List<Quiz> quizzes = quizService.getQuizzesByUser();
        List<QuizDTO> quizDTOs = quizzes.stream()
                .map(QuizDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quizDTOs);
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuizWithQuestions(@Valid @RequestBody QuizRequest request) {
        Quiz quiz = quizService.createQuizWithQuestions(request);
        return ResponseEntity.ok(QuizDTO.fromEntity(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(QuizDTO.fromEntity(quiz));
    }
}