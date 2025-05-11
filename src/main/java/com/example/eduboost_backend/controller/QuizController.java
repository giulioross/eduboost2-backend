package com.example.eduboost_backend.controller;


import com.example.eduboost_backend.dto.auth.MessageResponse;
import com.example.eduboost_backend.dto.quiz.CreateQuestionRequest;
import com.example.eduboost_backend.dto.quiz.CreateQuizRequest;
import com.example.eduboost_backend.dto.quiz.QuestionDTO;
import com.example.eduboost_backend.dto.quiz.QuizDTO;
import com.example.eduboost_backend.model.Question;
import com.example.eduboost_backend.model.Quiz;
import com.example.eduboost_backend.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<Quiz> quizzes = quizService.getQuizzesByUser();
        List<QuizDTO> quizDTOs = quizzes.stream()
                .map(QuizDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(quizDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        return ResponseEntity.ok(QuizDTO.fromEntity(quiz));
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@Valid @RequestBody CreateQuizRequest request) {
        Quiz quiz = quizService.createQuiz(request);
        return ResponseEntity.ok(QuizDTO.fromEntity(quiz));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable Long id, @Valid @RequestBody CreateQuizRequest request) {
        Quiz quiz = quizService.updateQuiz(id, request);
        return ResponseEntity.ok(QuizDTO.fromEntity(quiz));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok(new MessageResponse("Quiz deleted successfully!"));
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<QuestionDTO> addQuestion(@PathVariable Long quizId, @Valid @RequestBody CreateQuestionRequest request) {
        Question question = quizService.addQuestionToQuiz(quizId, request);
        return ResponseEntity.ok(QuestionDTO.fromEntity(question));
    }

    @PutMapping("/questions/{questionId}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long questionId, @Valid @RequestBody CreateQuestionRequest request) {
        Question question = quizService.updateQuestion(questionId, request);
        return ResponseEntity.ok(QuestionDTO.fromEntity(question));
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        quizService.deleteQuestion(questionId);
        return ResponseEntity.ok(new MessageResponse("Question deleted successfully!"));
    }

    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuestionDTO>> getQuestions(@PathVariable Long quizId) {
        List<Question> questions = quizService.getQuestionsByQuiz(quizId);
        List<QuestionDTO> questionDTOs = questions.stream()
                .map(QuestionDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(questionDTOs);
    }
}