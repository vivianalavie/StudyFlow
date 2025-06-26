package com.studyflow.service;

import com.studyflow.model.user.UserStudyPreference;
import com.studyflow.repository.UserStudyPreferenceRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserStudyPreferenceService {
    private final Jdbi jdbi;

    public UserStudyPreferenceService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<UserStudyPreference> getPreferencesByUser(UUID userId) {
        return jdbi.withExtension(UserStudyPreferenceRepository.class, repo -> repo.getPreferencesByUser(userId));
    }

    public void insertUserStudyPreference(UserStudyPreference preference) {
        jdbi.useExtension(UserStudyPreferenceRepository.class, repo -> repo.insertUserStudyPreference(preference));
    }

    public void replacePreferencesForUser(UUID userId, List<UserStudyPreference> preferences) {
        jdbi.useExtension(UserStudyPreferenceRepository.class, repo -> {
            repo.deletePreferencesByUser(userId);
            for (UserStudyPreference pref : preferences) {
                pref.setUserId(userId);
                if (pref.getId() == null) pref.setId(UUID.randomUUID());
                if (pref.getCreatedAt() == null) pref.setCreatedAt(LocalDateTime.now());
                repo.insertUserStudyPreference(pref);
            }
        });
    }
} 