package com.example.eduboost_backend.repository;


import com.example.eduboost_backend.model.Quiz;
import com.example.eduboost_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByUser(User user);

    List<Quiz> findByUserOrderByCreatedAtDesc(User user);

    List<Quiz> findBySubjectContainingIgnoreCase(String subject);

    List<Quiz> findByUserAndSubjectContainingIgnoreCase(User user, String subject);
}