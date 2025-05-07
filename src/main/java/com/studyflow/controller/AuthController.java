package com.studyflow.controller;

import com.studyflow.model.auth.SignUpResponse;
import com.studyflow.model.auth.UserCredentialsModel;
import com.studyflow.repository.ClerkUserRepository;
import com.studyflow.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository = new ClerkUserRepository();

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public SignUpResponse register(@RequestBody UserCredentialsModel user) { return userRepository.signup(user); }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") String userId) {
        userRepository.deleteUser(userId);
    }
}