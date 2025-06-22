package com.studyflow.service;

import com.studyflow.model.assignment.Assignment;
import com.studyflow.model.assignment.Difficulty;
import com.studyflow.model.course.Course;
import com.studyflow.model.StudySession.StudySession;
import com.studyflow.model.timeblocker.TimeBlocker;
import com.studyflow.model.user.UserCreation;
import com.studyflow.repository.AssignmentRepository;
import com.studyflow.repository.CourseRepository;
import com.studyflow.repository.TimeBlockerRepository;
import com.studyflow.repository.UserCreationRepository;
import com.studyflow.service.StudySessionService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    private final Jdbi jdbi;
    private final StudySessionService studySessionService;

    public SchedulerService(Jdbi jdbi, StudySessionService studySessionService) {
        this.jdbi = jdbi;
        this.studySessionService = studySessionService;
    }

    public void generateScheduleForAssignment(UUID assignmentId) {
        // 1. Assignment und User laden
        Assignment assignment = jdbi.withExtension(AssignmentRepository.class, repo ->
                repo.getAssignmentsByUserId(null) // Dummy, wir brauchen eine getById-Methode
        ).stream().filter(a -> a.getId().equals(assignmentId)).findFirst().orElseThrow();
        UUID userId = assignment.getCourseId(); // Dummy, muss angepasst werden
        UserCreation user = null; // Dummy, muss angepasst werden
        // TODO: Korrekte User-Ladung implementieren

        // 2. User-Präferenzen laden (Dummy)
        List<Preference> preferences = List.of(
                new Preference(PreferenceType.MORNING, 1),
                new Preference(PreferenceType.AFTERNOON, 2)
        );

        // 3. Benötigte Lernzeit berechnen
        Course course = jdbi.withExtension(CourseRepository.class, repo ->
                repo.findCoursesByUserId(userId)
        ).stream().findFirst().orElseThrow(); // Dummy, muss angepasst werden
        double requiredMinutes = course.getTotalWorkloadHours()
                * ((double) assignment.getTotalAchievablePoints() / course.getTotalPoints())
                * assignment.getDifficulty().getMultiplier() * 60;

        // 4. Blockierte Zeitfenster sammeln
        List<TimeBlocker> timeBlockers = jdbi.withExtension(TimeBlockerRepository.class, repo ->
                repo.getTimeBlockersByUser(userId)
        );
        List<StudySession> existingSessions = studySessionService.getStudySessionsByUser(userId);

        // 5. Für jeden Tag von heute bis Deadline: (Dummy-Logik)
        // ...
        // 6. Slots bewerten und Sessions platzieren (Dummy-Logik)
        // ...
        // 7. Sessions speichern (Dummy-Logik)
        // ...
    }

    // Dummy-Enums und Klassen für Präferenzen
    public enum PreferenceType { MORNING, AFTERNOON, EVENING, NIGHT }
    public static class Preference {
        public PreferenceType type;
        public int priority;
        public Preference(PreferenceType type, int priority) {
            this.type = type;
            this.priority = priority;
        }
    }
} 