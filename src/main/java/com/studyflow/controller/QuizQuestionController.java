package com.studyflow.controller;

import com.studyflow.model.QuizQuestion;
import com.studyflow.repository.QuizQuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.jdbi.v3.core.Jdbi;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuizQuestionController {

    private final QuizQuestionRepository repository;

    public QuizQuestionController(Jdbi jdbi) {
        this.repository = jdbi.onDemand(QuizQuestionRepository.class);
    }

    @Operation(summary = "Retrieves all active questions for the quiz")
    @GetMapping("/quiz")
    public List<QuizQuestion> getActiveQuizQuestions() {
        return repository.getActiveQuizQuestions();
    }
}
