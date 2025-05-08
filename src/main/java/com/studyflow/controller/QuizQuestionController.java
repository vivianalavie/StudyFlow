package com.studyflow.controller;

import com.studyflow.repository.SupabaseQuizRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class QuizQuestionController {

    @Operation(summary = "Retrieves all active questions for the quiz")
    @GetMapping("/quiz")
    public String quiz() {
        return "";
    }



@PostMapping("/quiz/answer")
@Operation(summary = "Submit a user's answer to a quiz question")
public ResponseEntity<String> submitAnswer(@RequestBody SupabaseQuizRepository submission) {
}
}
