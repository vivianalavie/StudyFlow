package com.studyflow.controller;

import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;
import com.studyflow.repository.SupabaseUserRepository;
import com.studyflow.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository = new SupabaseUserRepository();

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserCredentialsModel user) {
        return userRepository.signup(user);
    }

    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody UserCredentialsModel user) {
        return userRepository.login(user);
    }
}