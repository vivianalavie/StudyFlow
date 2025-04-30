package com.studyflow.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class QuizController {

    @Operation(summary = "Retrieves all active questions for the quiz")
    @GetMapping("/quiz")
    public String quiz() {
        return "";
    }

}
