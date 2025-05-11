package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.Quiz;
import com.example.eduboost_backend.model.QuizAttempt;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAttemptRepository extends JpaRepository<QuizAttempt, Long> {

    List<QuizAttempt> findByUser(User user);

    List<QuizAttempt> findByUserOrderByStartTimeDesc(User user);

    List<QuizAttempt> findByQuiz(Quiz quiz);

    List<QuizAttempt> findByUserAndQuiz(User user, Quiz quiz);

    List<QuizAttempt> findByUserAndCompleted(User user, boolean completed);
}