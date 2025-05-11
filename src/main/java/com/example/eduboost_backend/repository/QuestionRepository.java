package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.Question;
import com.example.eduboost_backend.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByQuiz(Quiz quiz);

    List<Question> findByQuizOrderByDifficultyAsc(Quiz quiz);

    List<Question> findByDifficultyBetween(Integer minDifficulty, Integer maxDifficulty);
}