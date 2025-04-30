package com.studyflow.controller;

import com.studyflow.model.LoginResponse;
import com.studyflow.model.User;
import com.studyflow.repository.SupabaseUserRepository;
import com.studyflow.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.studyflow.model.LoginRequest;


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
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = userRepository.login(request.getEmail(), request.getPassword());

            if (response != null && response.getAccess_token() != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed.");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
