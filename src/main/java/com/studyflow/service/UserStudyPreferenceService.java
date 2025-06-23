package com.studyflow.service;

import com.studyflow.model.user.UserStudyPreference;
import com.studyflow.repository.UserStudyPreferenceRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

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
} 