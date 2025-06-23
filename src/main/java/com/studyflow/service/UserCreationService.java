package com.studyflow.service;

import com.studyflow.model.user.UserCreation;
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
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Anlegen des Users", e);
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

}
