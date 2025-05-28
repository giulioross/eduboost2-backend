package com.example.eduboost_backend.service;


import com.example.eduboost_backend.dto.quiz.*;
import com.example.eduboost_backend.model.Option;
import com.example.eduboost_backend.model.Question;
import com.example.eduboost_backend.model.Quiz;
import com.example.eduboost_backend.model.User;
import com.example.eduboost_backend.repository.OptionRepository;
import com.example.eduboost_backend.repository.QuestionRepository;
import com.example.eduboost_backend.repository.QuizRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private OptionRepository optionRepository;

    @Autowired
    private UserService userService;

    public List<Quiz> getQuizzesByUser() {
        User currentUser = userService.getCurrentUser();
        return quizRepository.findByUserWithQuestions(currentUser);
    }

    public Quiz getQuizById(Long id) {
        User currentUser = userService.getCurrentUser();
        Quiz quiz = quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));

        if (!quiz.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to access this quiz");
        }

        return quiz;
    }

    @Transactional
    public Quiz createQuiz(CreateQuizRequest request) {
        User currentUser = userService.getCurrentUser();

        Quiz quiz = new Quiz();
        quiz.setUser(currentUser);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setSubject(request.getSubject());
        quiz.setTopic(request.getTopic());
        quiz.setQuizType(Quiz.QuizType.valueOf(request.getQuizType().name()));
        quiz.setAdaptive(request.isAdaptive());
        quiz.setTimeLimit(request.getTimeLimit());

        return quizRepository.save(quiz);
    }

    @Transactional
    public Quiz createQuizWithQuestions(@Valid QuizRequest request) {
        User currentUser = userService.getCurrentUser();

        Quiz quiz = new Quiz();
        quiz.setUser(currentUser);
        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setSubject(request.getSubject());
        quiz.setTopic(request.getTopic());
        quiz.setQuizType(request.getQuizType());
        quiz.setAdaptive(request.isAdaptive());
        quiz.setTimeLimit(request.getTimeLimit());

        // Save quiz first to get ID
        Quiz savedQuiz = quizRepository.save(quiz);

        // Add all questions
        for (CreateQuestionRequest questionRequest : request.getQuestions()) {
            Question question = new Question();
            question.setQuiz(savedQuiz);
            question.setQuestionText(questionRequest.getQuestionText());
            question.setQuestionType(questionRequest.getQuestionType());
            question.setDifficulty(questionRequest.getDifficulty());
            question.setPoints(questionRequest.getPoints());
            question.setExplanation(questionRequest.getExplanation());
            question.setCorrectAnswer(questionRequest.getCorrectAnswer());

            // Save question first to get ID
            Question savedQuestion = questionRepository.save(question);

            // Add options if applicable
            if (questionRequest.getOptions() != null && !questionRequest.getOptions().isEmpty()) {
                for (CreateOptionRequest optionRequest : questionRequest.getOptions()) {
                    Option option = new Option();
                    option.setQuestion(savedQuestion);
                    option.setOptionText(optionRequest.getOptionText());
                    option.setCorrect(optionRequest.isCorrect());
                    optionRepository.save(option);
                }
            }
        }

        // Refresh quiz to get all questions
        return quizRepository.findById(savedQuiz.getId()).orElseThrow(() -> 
            new RuntimeException("Quiz not found after creation"));
    }

    @Transactional
    public Quiz updateQuiz(Long id, CreateQuizRequest request) {
        Quiz quiz = getQuizById(id);

        quiz.setTitle(request.getTitle());
        quiz.setDescription(request.getDescription());
        quiz.setSubject(request.getSubject());
        quiz.setTopic(request.getTopic());
        quiz.setQuizType(request.getQuizType());
        quiz.setAdaptive(request.isAdaptive());
        quiz.setTimeLimit(request.getTimeLimit());

        return quizRepository.save(quiz);
    }

    @Transactional
    public void deleteQuiz(Long id) {
        Quiz quiz = getQuizById(id);
        quizRepository.delete(quiz);
    }

    @Transactional
    public Question addQuestionToQuiz(Long quizId, CreateQuestionRequest request) {
        Quiz quiz = getQuizById(quizId);

        Question question = new Question();
        question.setQuiz(quiz);
        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setDifficulty(request.getDifficulty());
        question.setPoints(request.getPoints());
        question.setExplanation(request.getExplanation());
        question.setCorrectAnswer(request.getCorrectAnswer());

        Question savedQuestion = questionRepository.save(question);

        // Add options if applicable
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            for (CreateOptionRequest optionRequest : request.getOptions()) {
                Option option = new Option();
                option.setQuestion(savedQuestion);
                option.setOptionText(optionRequest.getOptionText());
                option.setCorrect(optionRequest.isCorrect());
                optionRepository.save(option);
            }
        }

        return savedQuestion;
    }

    @Transactional
    public Question updateQuestion(Long questionId, CreateQuestionRequest request) {
        User currentUser = userService.getCurrentUser();
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        if (!question.getQuiz().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to update this question");
        }

        question.setQuestionText(request.getQuestionText());
        question.setQuestionType(request.getQuestionType());
        question.setDifficulty(request.getDifficulty());
        question.setPoints(request.getPoints());
        question.setExplanation(request.getExplanation());
        question.setCorrectAnswer(request.getCorrectAnswer());

        Question savedQuestion = questionRepository.save(question);

        // Delete existing options
        List<Option> existingOptions = optionRepository.findByQuestion(savedQuestion);
        optionRepository.deleteAll(existingOptions);

        // Add new options
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            for (CreateOptionRequest optionRequest : request.getOptions()) {
                Option option = new Option();
                option.setQuestion(savedQuestion);
                option.setOptionText(optionRequest.getOptionText());
                option.setCorrect(optionRequest.isCorrect());
                optionRepository.save(option);
            }
        }

        return savedQuestion;
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        User currentUser = userService.getCurrentUser();
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));

        if (!question.getQuiz().getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You don't have permission to delete this question");
        }

        questionRepository.delete(question);
    }

    public List<Question> getQuestionsByQuiz(Long quizId) {
        Quiz quiz = getQuizById(quizId);
        return questionRepository.findByQuiz(quiz);
    }
}