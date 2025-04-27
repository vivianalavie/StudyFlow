package com.studyflow.controller;

import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String register() {
        return "not implemented";
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public String login() {
        return "not implemented";
    }
}
