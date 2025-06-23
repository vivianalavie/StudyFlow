package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.user.UserStudyPreference;
import com.studyflow.service.UserStudyPreferenceService;
import com.studyflow.service.UserCreationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}