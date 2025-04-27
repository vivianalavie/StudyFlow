package com.studyflow.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @PostMapping("/auth/register")
    public String register() {
        return "not implemented";
    }

}
