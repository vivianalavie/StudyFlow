package com.studyflow.controller;

import com.studyflow.model.User;
import com.studyflow.repository.SupabaseUserRepository;
import com.studyflow.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserRepository userRepository = new SupabaseUserRepository();

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "User registered successfully";
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public String login() {
        return "not implemented";
    }
}
