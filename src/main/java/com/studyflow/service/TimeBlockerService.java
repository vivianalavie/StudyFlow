package com.studyflow.service;

import com.studyflow.model.timeblocker.TimeBlocker;
import com.studyflow.repository.TimeBlockerRepository;
import com.studyflow.repository.UserCreationRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TimeBlockerService {

    private final Jdbi jdbi;

    public TimeBlockerService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createTimeBlocker(TimeBlocker timeBlocker) {
        if (timeBlocker.getId() == null) {
            timeBlocker.setId(UUID.randomUUID());
        }
        jdbi.useExtension(TimeBlockerRepository.class, repo -> repo.insertTimeBlocker(timeBlocker));
    }

    public void updateTimeBlocker(UUID id, TimeBlocker timeBlocker) {
        timeBlocker.setId(id);
        jdbi.useExtension(TimeBlockerRepository.class, repo -> repo.updateTimeBlocker(timeBlocker));
    }

    public void deleteTimeBlocker(UUID id) {
        jdbi.useExtension(TimeBlockerRepository.class, repo -> repo.deleteTimeBlocker(id));
    }

    public List<TimeBlocker> getTimeBlockersByUser(UUID userId) {
        return jdbi.withExtension(TimeBlockerRepository.class, repo -> repo.getTimeBlockersByUser(userId));
    }

    public UUID getUserIdByClerkId(String clerkUserId) {
        return jdbi.withExtension(UserCreationRepository.class, repo ->
                repo.findUserIdByClerkUserId(clerkUserId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found for clerk ID: " + clerkUserId))
        );
    }
}
