package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.user.UserStudyPreference;
import com.studyflow.service.UserStudyPreferenceService;
import com.studyflow.service.UserCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-study-preferences")
public class UserStudyPreferenceController {
    private final UserStudyPreferenceService userStudyPreferenceService;
    private final UserCreationService userCreationService;

    public UserStudyPreferenceController(UserStudyPreferenceService userStudyPreferenceService, UserCreationService userCreationService) {
        this.userStudyPreferenceService = userStudyPreferenceService;
        this.userCreationService = userCreationService;
    }

    @GetMapping("/my")
    public ResponseEntity<List<UserStudyPreference>> getMyPreferences(@CurrentUser String clerkUserId) {
        UUID userId = userCreationService.getUserIdByClerkId(clerkUserId);
        List<UserStudyPreference> preferences = userStudyPreferenceService.getPreferencesByUser(userId);
        return ResponseEntity.ok(preferences);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> addMyPreference(@RequestBody UserStudyPreference preference, @CurrentUser String clerkUserId) {
        UUID userId = userCreationService.getUserIdByClerkId(clerkUserId);
        preference.setUserId(userId);
        if (preference.getId() == null) preference.setId(UUID.randomUUID());
        if (preference.getCreatedAt() == null) preference.setCreatedAt(java.time.LocalDateTime.now());
        userStudyPreferenceService.insertUserStudyPreference(preference);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/udpate")
    public ResponseEntity<Void> updateMyPreferences(@RequestBody List<UserStudyPreference> preferences, @CurrentUser String clerkUserId) {
        UUID userId = userCreationService.getUserIdByClerkId(clerkUserId);
        userStudyPreferenceService.replacePreferencesForUser(userId, preferences);
        return ResponseEntity.ok().build();
    }
}