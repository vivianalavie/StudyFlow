package com.studyflow.controller;


import com.google.gson.Gson;
import com.studyflow.model.survey.Questions;
import com.studyflow.repository.SupabaseQuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
