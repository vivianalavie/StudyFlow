package com.studyflow.service;

import com.studyflow.model.user.UserCreation;
import com.studyflow.repository.UserCreationRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

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
}
