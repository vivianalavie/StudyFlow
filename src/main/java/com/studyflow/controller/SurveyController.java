package com.studyflow.controller;


import com.google.gson.Gson;
import com.studyflow.model.survey.Questions;
import com.studyflow.repository.SupabaseQuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/survey")
public class SurveyController {

    private final SupabaseQuestionRepository questionRepository = new SupabaseQuestionRepository();

    /**
     * Retrieves all active questions from SupaBase for the quiz.
     *
     * @return a list of all quiz questions
     */

    @Operation(summary = "Retrieves all active questions for the quiz")
    @GetMapping("/questions")
    public List<Questions> getQuestions() {
        return questionRepository.getAllQuestions(); //Gets all questions from SupaBase
    }

    @PostMapping("/quiz/answer")
    @Operation(summary = "Submit a user's answer to a quiz question")
    public ResponseEntity<String> submitAnswer(@RequestBody QuizAnswer submission) {
        // Beispielhafte Speicherung der Antwort – anpassen je nach Bedarf
        quizRepository.save(submission); // vorausgesetzt, quizRepository kann das
        return ResponseEntity.ok("Antwort erfolgreich übermittelt");
    }

}
