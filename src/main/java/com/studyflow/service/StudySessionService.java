package com.studyflow.service;

import com.studyflow.model.StudySession.StudySession;
import com.studyflow.repository.StudySessionRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudySessionService {
    private final Jdbi jdbi;

    public StudySessionService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createStudySession(StudySession studySession) {
        if (studySession.getId() == null) {
            studySession.setId(UUID.randomUUID());
        }
        jdbi.useExtension(StudySessionRepository.class, repo -> repo.insertStudySession(studySession));
    }

    public List<StudySession> getStudySessionsByUser(UUID userId) {
        return jdbi.withExtension(StudySessionRepository.class, repo -> repo.getStudySessionsByUser(userId));
    }

    public List<StudySession> getStudySessionsByAssignment(UUID assignmentId) {
        return jdbi.withExtension(StudySessionRepository.class, repo -> repo.getStudySessionsByAssignment(assignmentId));
    }

    public void deleteStudySessionsByAssignment(UUID assignmentId) {
        jdbi.useExtension(StudySessionRepository.class, repo -> repo.deleteStudySessionsByAssignment(assignmentId));
    }
} 