package com.studyflow.controller;

import com.studyflow.exception.SignupFlowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignupFlowException.class)
    public ResponseEntity<String> handleSignupFlowException(
            SignupFlowException ex
    ) {
        if (ex instanceof SignupFlowException.PasswordLeaked) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Your password has been found in a data breach. Please choose a stronger password.");
        } else if (ex instanceof SignupFlowException.InvalidEmail) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Please enter a valid email address.");
        } else if (ex instanceof SignupFlowException.UserExists) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("A user with that email address already exists.");
        }
        else if (ex instanceof SignupFlowException.PasswordTooShort) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Password is too short");
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred during signup.");
        }
    }
}
