package com.studyflow.service;

import com.studyflow.model.user.UserCreation;
import com.studyflow.model.user.UserPreferences;
import com.studyflow.repository.UserCreationRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCreationService {

    private final Jdbi jdbi;

    public UserCreationService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createUser(UserCreation user, String clerkUserId) {
        try {
            jdbi.useExtension(UserCreationRepository.class, repo ->
                    repo.insertUser(user, clerkUserId)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error while creating user", e);
        }
    }

    public boolean userExists(String clerkUserId) {
        return jdbi.withExtension(UserCreationRepository.class, repo ->
                repo.existsByClerkUserId(clerkUserId)
        );
    }

    public UUID getUserIdByClerkId(String clerkUserId) {
        return jdbi.withExtension(UserCreationRepository.class, repo ->
            repo.findUserIdByClerkUserId(clerkUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for clerk ID: " + clerkUserId))
        );
    }

    public UserPreferences getUserPreferences(String clerkUserId) {
        return jdbi.withExtension(UserCreationRepository.class, repo ->
            repo.getUserPreferencesByClerkUserId(clerkUserId)
                .orElseThrow(() -> new IllegalArgumentException("User preferences not found for clerk ID: " + clerkUserId))
        );
    }

    public void updateUserPreferences(UserPreferences preferences, String clerkUserId) {
        try {
            jdbi.useExtension(UserCreationRepository.class, repo ->
                repo.updateUserPreferences(preferences, clerkUserId)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error while updating user preferences", e);
        }
    }

}
