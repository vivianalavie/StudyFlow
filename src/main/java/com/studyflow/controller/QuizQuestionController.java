package com.studyflow.controller;


import com.google.gson.Gson;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api")
public class QuizQuestionController {

    @Operation(summary = "Retrieves all active questions for the quiz")
    @GetMapping("/quiz")
    public String quiz() {
        List<Map<String, Object>> questions = new ArrayList<>();

        Map<String, Object> q1 = new HashMap<>();
        q1.put("uuid", UUID.randomUUID().toString());
        q1.put("question", "What is the capital of France?");
        q1.put("options", Arrays.asList("Paris", "London", "Berlin", "Madrid"));

        Map<String, Object> q2 = new HashMap<>();
        q2.put("uuid", UUID.randomUUID().toString());
        q2.put("question", "Which planet is known as the Red Planet?");
        q2.put("options", Arrays.asList("Earth", "Mars", "Jupiter", "Venus"));

        questions.add(q1);
        questions.add(q2);

        Gson gson = new Gson();
        return gson.toJson(questions);
    }

}
