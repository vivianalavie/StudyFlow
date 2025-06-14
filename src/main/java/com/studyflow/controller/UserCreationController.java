package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.user.UserCreation;
import com.studyflow.service.UserCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserCreationController {

    private final UserCreationService userCreationService;

    public UserCreationController(UserCreationService userCreationService) {
        this.userCreationService = userCreationService;
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserCreation request, @CurrentUser String clerkUserId) {
        userCreationService.createUser(request, clerkUserId);
        System.out.println("✅ clerkUserId = " + clerkUserId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> doesUserExist(@CurrentUser String clerkUserId) {
        boolean exists = userCreationService.userExists(clerkUserId);
        return ResponseEntity.ok(exists);
    }


}
