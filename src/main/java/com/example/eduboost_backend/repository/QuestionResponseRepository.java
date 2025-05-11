package com.example.eduboost_backend.repository;


import com.example.eduboost_backend.model.Question;
import com.example.eduboost_backend.model.QuestionResponse;
import com.example.eduboost_backend.model.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {

    List<QuestionResponse> findByAttempt(QuizAttempt attempt);

    List<QuestionResponse> findByQuestion(Question question);

    List<QuestionResponse> findByAttemptAndIsCorrect(QuizAttempt attempt, boolean isCorrect);

    QuestionResponse findByAttemptAndQuestion(QuizAttempt attempt, Question question);
}