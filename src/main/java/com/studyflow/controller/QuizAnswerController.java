package com.studyflow.controller;

import com.studyflow.repository.QuizAnswerRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.studyflow.model.survey.QuizAnswer;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/quiz-answers")
public class QuizAnswerController {

    private final QuizAnswerRepository repository;

    public QuizAnswerController(Jdbi jdbi) {
        this.repository = jdbi.onDemand(QuizAnswerRepository.class);
    }

    @PostMapping
    public void saveAnswer(@RequestBody QuizAnswer answer) {
        answer.setId(UUID.randomUUID());
        answer.setCreatedOn(OffsetDateTime.now());
        repository.insertQuizAnswer(answer);
    }


}
