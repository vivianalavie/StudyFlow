package com.studyflow.controller;

import com.studyflow.model.auth.AuthResponse;
import com.studyflow.model.auth.UserCredentialsModel;
import com.studyflow.repository.SupabaseUserRepository;
import com.studyflow.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    UserRepository userRepository = new SupabaseUserRepository();

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserCredentialsModel user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }
    @Operation(summary = "Login a user")
    @PostMapping("/login")
    public String login(@RequestBody UserCredentialsModel user) {
        return "Not impl";
    }
}
