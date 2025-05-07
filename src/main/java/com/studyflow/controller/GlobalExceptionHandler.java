package com.studyflow.controller;

import com.studyflow.exception.SignupFlowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignupFlowException.SignupError.class)
    public ResponseEntity<String> handleSignupErrorException(SignupFlowException.SignupError ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("We failed to sign you up. Please try again later");
    }

    @ExceptionHandler(SignupFlowException.PasswordLeaked.class)
    public ResponseEntity<String> handlePasswordLeakedException(SignupFlowException.PasswordLeaked ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your password has been found in a data breach");
    }

    @ExceptionHandler(SignupFlowException.InvalidEmail.class)
    public ResponseEntity<String> handleInvalidEmailException(SignupFlowException.InvalidEmail ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The email provided is invalid");
    }

    @ExceptionHandler(SignupFlowException.UserExists.class)
    public ResponseEntity<String> handleDuplicateUser(SignupFlowException.UserExists ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("The email you provided is already associated with another user");
    }

    //Optional: Generic handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}
