package com.example.eduboost_backend.repository;

import com.example.eduboost_backend.model.Option;
import com.example.eduboost_backend.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<Option, Long> {

    List<Option> findByQuestion(Question question);

    List<Option> findByQuestionAndIsCorrect(Question question, boolean isCorrect);
}