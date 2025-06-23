package com.studyflow.service;

import com.studyflow.model.assignment.Assignment;
import com.studyflow.model.assignment.Difficulty;
import com.studyflow.model.course.Course;
import com.studyflow.model.StudySession.StudySession;
import com.studyflow.model.timeblocker.TimeBlocker;
import com.studyflow.model.user.UserCreation;
import com.studyflow.model.user.UserStudyPreference;
import com.studyflow.repository.AssignmentRepository;
import com.studyflow.repository.CourseRepository;
import com.studyflow.repository.TimeBlockerRepository;
import com.studyflow.repository.UserCreationRepository;
import com.studyflow.service.StudySessionService;
import com.studyflow.service.UserStudyPreferenceService;
import com.studyflow.service.UserCreationService;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchedulerService {
    private final Jdbi jdbi;
    private final StudySessionService studySessionService;
    private final UserStudyPreferenceService userStudyPreferenceService;
    private final UserCreationService userCreationService;

    public SchedulerService(Jdbi jdbi, StudySessionService studySessionService, UserStudyPreferenceService userStudyPreferenceService, UserCreationService userCreationService) {
        this.jdbi = jdbi;
        this.studySessionService = studySessionService;
        this.userStudyPreferenceService = userStudyPreferenceService;
        this.userCreationService = userCreationService;
    }

    public void generateScheduleForAssignment(UUID assignmentId) {
        // Bereits existierende StudySessions für dieses Assignment löschen
        studySessionService.deleteStudySessionsByAssignment(assignmentId);
        // 1. Assignment, Course und User laden
        Assignment assignment = jdbi.withExtension(AssignmentRepository.class, repo ->
                repo.getAssignmentById(assignmentId)
        );
        Course course = jdbi.withExtension(CourseRepository.class, repo ->
                repo.getCourseById(assignment.getCourseId())
        );
        System.out.println("[Scheduler] Kurs-ID: " + course.getId());
        System.out.println("[Scheduler] Ersteller (createdBy): " + course.getCreatedBy());
        System.out.println("[Scheduler] Name: " + course.getName());
        System.out.println("[Scheduler] Beschreibung: " + course.getDescription());
        System.out.println("[Scheduler] Startdatum: " + course.getStartDate());
        System.out.println("[Scheduler] Enddatum: " + course.getEndDate());
        System.out.println("[Scheduler] Professor: " + course.getProfessorName());
        System.out.println("[Scheduler] Gesamtpunkte: " + course.getTotalPoints());
        System.out.println("[Scheduler] Gesamtarbeitsstunden: " + course.getTotalWorkloadHours());
        System.out.println("[Scheduler] Selbstlern-Stunden: " + course.getTotalSelfWorkHours());
        System.out.println("[Scheduler] Farbe: " + course.getColor());
        UUID userId = jdbi.withExtension(CourseRepository.class, repo ->
            repo.getCreatedByUserIdByCourseId(course.getId())
        );
        System.out.println("[Scheduler] UserId (created_by_user_id): " + userId);
        UserCreation user = getUserById(userId);
        // TODO: UserCreation korrekt laden (z.B. über userId)

        // 2. User-Präferenzen laden
        final List<UserStudyPreference> preferences = userStudyPreferenceService.getPreferencesByUser(userId)
            .stream().sorted(Comparator.comparingInt(UserStudyPreference::getPriority)).toList();

        // 3. Benötigte Lernzeit berechnen
        double requiredMinutes = course.getTotalWorkloadHours()
                * ((double) assignment.getTotalAchievablePoints() / course.getTotalPoints())
                * assignment.getDifficulty().getMultiplier() * 60;

        // 4. Blockierte Zeitfenster sammeln
        List<TimeBlocker> timeBlockers = jdbi.withExtension(TimeBlockerRepository.class, repo ->
                repo.getTimeBlockersByUser(userId)
        );
        List<StudySession> existingSessions = studySessionService.getStudySessionsByUser(userId);

        // 5. Blackout-Weekdays
        List<String> blackoutWeekdays = user.getBlackoutWeekdays();

        // 6. Slot-Berechnung und Scheduling
        LocalDate today = LocalDate.now();
        LocalDate deadline = assignment.getDeadline().toLocalDate();
        List<StudySession> sessionsToSave = new ArrayList<>();
        double minSessionLength = 15;
        double maxSessionLength = user.getMaxStudyDuration();
        double minutesLeft = requiredMinutes;

        Map<LocalDate, List<TimeSlot>> freeSlotsPerDay = new HashMap<>();
        for (LocalDate date = today; !date.isAfter(deadline); date = date.plusDays(1)) {
            // Blackout-Check
            if (blackoutWeekdays != null && blackoutWeekdays.contains(date.getDayOfWeek().name())) continue;
            // Tageslernfenster
            LocalTime start = user.getStartLearningTime();
            LocalTime end = user.getEndLearningTime();
            List<TimeSlot> daySlots = new ArrayList<>();
            if (start.isBefore(end)) {
                daySlots.add(new TimeSlot(date.atTime(start), date.atTime(end)));
            } else {
                // Über Mitternacht (z.B. 22:00-02:00)
                daySlots.add(new TimeSlot(date.atTime(start), date.plusDays(1).atTime(end)));
            }
            // Blocker abziehen
            List<TimeSlot> blockers = new ArrayList<>();
            for (TimeBlocker tb : timeBlockers) {
                if (!tb.getStartDate().toLocalDate().isAfter(date) && !tb.getEndDate().toLocalDate().isBefore(date)) {
                    blockers.add(new TimeSlot(tb.getStartDate().toLocalDate().equals(date) ? tb.getStartDate().toLocalTime() : LocalTime.MIN,
                                              tb.getEndDate().toLocalDate().equals(date) ? tb.getEndDate().toLocalTime() : LocalTime.MAX,
                                              date));
                }
            }
            for (StudySession ss : existingSessions) {
                if (ss.getStartTime().toLocalDate().equals(date)) {
                    blockers.add(new TimeSlot(ss.getStartTime().toLocalTime(), ss.getEndTime().toLocalTime(), date));
                }
            }
            // Gaps berechnen
            List<TimeSlot> free = subtractBlockers(daySlots, blockers);
            freeSlotsPerDay.put(date, free);
        }

        // 7. Slot-Scoring und Sessions platzieren
        while (minutesLeft > 0) {
            boolean anyPlaced = false;
            for (LocalDate date = today; !date.isAfter(deadline); date = date.plusDays(1)) {
                List<TimeSlot> slots = freeSlotsPerDay.getOrDefault(date, new ArrayList<>());
                if (slots.isEmpty()) continue;
                // Scoring
                slots.sort((a, b) -> scoreSlot(b, preferences) - scoreSlot(a, preferences));
                TimeSlot best = slots.get(0);
                long slotMinutes = best.lengthInMinutes();
                long sessionLength = (long) Math.min(Math.min(maxSessionLength, minutesLeft), slotMinutes);
                if (sessionLength < minSessionLength) continue;
                // Session anlegen
                StudySession session = new StudySession();
                session.setId(UUID.randomUUID());
                session.setUserId(userId);
                session.setStartTime(best.getStart());
                session.setEndTime(best.getStart().plusMinutes(sessionLength));
                session.setCreatedOn(LocalDateTime.now());
                session.setToDoDescription(assignment.getTitle());
                session.setAssignmentId(assignment.getId());
                System.out.println("[Scheduler] Neue geplante Session: " + session);
                studySessionService.createStudySession(session);
                System.out.println("[Scheduler] Session gespeichert!");
                minutesLeft -= sessionLength;
                // Nach der Session eine halbe Stunde Pause als Blocker einplanen
                LocalDateTime pauseStart = best.getStart().plusMinutes(sessionLength);
                LocalDateTime pauseEnd = pauseStart.plusMinutes(30);
                List<TimeSlot> neueSlots = new ArrayList<>();
                for (TimeSlot slot : slots) {
                    if (slot.equals(best)) {
                        // Slot wird durch Session und Pause zerschnitten
                        if (slot.getEnd().isAfter(pauseEnd)) {
                            // Es bleibt nach Pause noch ein Slot übrig
                            if (Duration.between(pauseEnd, slot.getEnd()).toMinutes() >= minSessionLength) {
                                neueSlots.add(new TimeSlot(pauseEnd, slot.getEnd()));
                            }
                        }
                        if (slot.getStart().isBefore(best.getStart())) {
                            // Es bleibt vor der Session noch ein Slot übrig
                            if (Duration.between(slot.getStart(), best.getStart()).toMinutes() >= minSessionLength) {
                                neueSlots.add(new TimeSlot(slot.getStart(), best.getStart()));
                            }
                        }
                    } else {
                        neueSlots.add(slot);
                    }
                }
                freeSlotsPerDay.put(date, neueSlots);
                anyPlaced = true;
                if (minutesLeft <= 0) break;
            }
            if (!anyPlaced) break; // Keine passenden Slots mehr
        }
        // 8. Speichern
        for (StudySession s : sessionsToSave) {
            studySessionService.createStudySession(s);
        }
    }

    // Hilfsklasse für TimeSlot (ohne Zeitzone)
    public static class TimeSlot {
        private final LocalDateTime start;
        private final LocalDateTime end;
        public TimeSlot(LocalDateTime start, LocalDateTime end) {
            this.start = start;
            this.end = end;
        }
        public TimeSlot(LocalTime start, LocalTime end, LocalDate date) {
            this.start = date.atTime(start);
            this.end = date.atTime(end);
        }
        public LocalDateTime getStart() { return start; }
        public LocalDateTime getEnd() { return end; }
        public long lengthInMinutes() { return java.time.Duration.between(start, end).toMinutes(); }
    }

    // Blocker von Slots abziehen (vereinfachte Logik)
    private List<TimeSlot> subtractBlockers(List<TimeSlot> slots, List<TimeSlot> blockers) {
        List<TimeSlot> result = new ArrayList<>(slots);
        for (TimeSlot blocker : blockers) {
            List<TimeSlot> newResult = new ArrayList<>();
            for (TimeSlot slot : result) {
                if (blocker.getEnd().isBefore(slot.getStart()) || blocker.getStart().isAfter(slot.getEnd())) {
                    newResult.add(slot);
                } else {
                    if (blocker.getStart().isAfter(slot.getStart())) {
                        newResult.add(new TimeSlot(slot.getStart(), blocker.getStart()));
                    }
                    if (blocker.getEnd().isBefore(slot.getEnd())) {
                        newResult.add(new TimeSlot(blocker.getEnd(), slot.getEnd()));
                    }
                }
            }
            result = newResult;
        }
        return result;
    }

    // Slot-Scoring nach Präferenz
    private int scoreSlot(TimeSlot slot, List<UserStudyPreference> preferences) {
        // Zeitbereiche für Präferenzen
        Map<UserStudyPreference.PreferenceType, int[]> prefRanges = Map.of(
                UserStudyPreference.PreferenceType.MORNING, new int[]{5, 12},
                UserStudyPreference.PreferenceType.AFTERNOON, new int[]{12, 17},
                UserStudyPreference.PreferenceType.EVENING, new int[]{17, 23},
                UserStudyPreference.PreferenceType.NIGHT, new int[]{23, 29} // 29 = 5 Uhr am Folgetag
        );
        int[] slotRange = {slot.getStart().getHour(), slot.getEnd().getHour()};
        int bestScore = 0;
        for (int i = 0; i < preferences.size(); i++) {
            UserStudyPreference.PreferenceType type = preferences.get(i).getPreferenceType();
            int[] range = prefRanges.get(type);
            int overlap = Math.max(0, Math.min(slotRange[1], range[1]) - Math.max(slotRange[0], range[0]));
            int slotLen = slotRange[1] - slotRange[0];
            double percent = slotLen > 0 ? (double) overlap / slotLen : 0;
            if (percent > 0.5) {
                bestScore = Math.max(bestScore, 2 - i); // 2 für erste Präferenz, 1 für zweite
            } else if (percent > 0) {
                bestScore = Math.max(bestScore, 1 - i); // 1 für zweite Präferenz
            }
        }
        return bestScore;
    }

    // Hilfsmethode, um UserCreation anhand der userId zu laden
    private UserCreation getUserById(UUID userId) {
        return jdbi.withExtension(UserCreationRepository.class, repo -> repo.getUserById(userId));
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