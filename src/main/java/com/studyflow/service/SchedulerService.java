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
        // Delete existing StudySessions for this assignment
        studySessionService.deleteStudySessionsByAssignment(assignmentId);
        // 1. Load Assignment, Course and User
        Assignment assignment = jdbi.withExtension(AssignmentRepository.class, repo ->
                repo.getAssignmentById(assignmentId)
        );
        Course course = jdbi.withExtension(CourseRepository.class, repo ->
                repo.getCourseById(assignment.getCourseId())
        );
        UUID userId = jdbi.withExtension(CourseRepository.class, repo ->
            repo.getCreatedByUserIdByCourseId(course.getId())
        );
        UserCreation user = getUserById(userId);
        // TODO: Load UserCreation correctly (e.g. via userId)

        // 2. Load user preferences
        final List<UserStudyPreference> preferences = userStudyPreferenceService.getPreferencesByUser(userId)
            .stream().sorted(Comparator.comparingInt(UserStudyPreference::getPriority)).toList();

        // 3. Calculate required study time
        double requiredMinutes = course.getTotalSelfWorkHours()
                * ((double) assignment.getTotalAchievablePoints() / course.getTotalPoints())
                * assignment.getDifficulty().getMultiplier() * 60;

        // 4. Collect blocked time windows
        List<TimeBlocker> timeBlockers = jdbi.withExtension(TimeBlockerRepository.class, repo ->
                repo.getTimeBlockersByUser(userId)
        );
        List<StudySession> existingSessions = studySessionService.getStudySessionsByUser(userId);

        // 5. Blackout weekdays
        List<String> blackoutWeekdays = user.getBlackoutWeekdays();

        // 6. Slot calculation and scheduling
        LocalDate today = LocalDate.now();
        LocalDate deadline = assignment.getDeadline().toLocalDate();
        List<StudySession> sessionsToSave = new ArrayList<>();
        double minSessionLength = 15;
        double maxSessionLength = user.getMaxStudyDuration();
        double minutesLeft = requiredMinutes;
        int pauseLength = 30;

        // 1. Determine free slots per day (as before)
        Map<LocalDate, List<TimeSlot>> freeSlotsPerDay = new HashMap<>();
        for (LocalDate date = today; !date.isAfter(deadline); date = date.plusDays(1)) {
            if (blackoutWeekdays != null && blackoutWeekdays.contains(date.getDayOfWeek().name())) {
                continue;
            }
            LocalTime start = user.getStartLearningTime();
            LocalTime end = user.getEndLearningTime();
            List<TimeSlot> daySlots = new ArrayList<>();
            if (start.isBefore(end)) {
                daySlots.add(new TimeSlot(date.atTime(start), date.atTime(end)));
            } else {
                daySlots.add(new TimeSlot(date.atTime(start), date.plusDays(1).atTime(end)));
            }
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
            List<TimeSlot> free = subtractBlockers(daySlots, blockers);
            freeSlotsPerDay.put(date, free);
        }

        // 2. Create and collect subslots
        class ScoredSubslot {
            TimeSlot slot;
            LocalDate date;
            int score;
            ScoredSubslot(TimeSlot slot, LocalDate date, int score) {
                this.slot = slot;
                this.date = date;
                this.score = score;
            }
        }
        List<ScoredSubslot> allSubslots = new ArrayList<>();
        for (Map.Entry<LocalDate, List<TimeSlot>> entry : freeSlotsPerDay.entrySet()) {
            LocalDate date = entry.getKey();
            // Check if the day is a blackout day
            if (blackoutWeekdays != null && blackoutWeekdays.contains(date.getDayOfWeek().name().toLowerCase())) {
                continue;
            }
            for (TimeSlot slot : entry.getValue()) {
                LocalDateTime slotStart = slot.getStart();
                LocalDateTime slotEnd = slot.getEnd();
                long totalMinutes = Duration.between(slotStart, slotEnd).toMinutes();
                long subslotLength = (long) maxSessionLength;
                long step = subslotLength + pauseLength;
                List<TimeSlot> subslotsOfThisSlot = new ArrayList<>();
                for (long offset = 0; offset + subslotLength <= totalMinutes; offset += step) {
                    LocalDateTime subStart = slotStart.plusMinutes(offset);
                    LocalDateTime subEnd = subStart.plusMinutes(subslotLength);
                    if (subEnd.isAfter(slotEnd)) break;
                    TimeSlot subslot = new TimeSlot(subStart, subEnd);
                    int score = scoreSlot(subslot, preferences);
                    allSubslots.add(new ScoredSubslot(subslot, date, score));
                    subslotsOfThisSlot.add(subslot);
                }
                // Extension: Check if another subslot without pause fits at the end
                long lastPossibleStartMinutes = totalMinutes - subslotLength;
                if (lastPossibleStartMinutes >= 0) {
                    LocalDateTime lastSubStart = slotStart.plusMinutes(lastPossibleStartMinutes);
                    LocalDateTime lastSubEnd = lastSubStart.plusMinutes(subslotLength);
                    // Check if this subslot doesn't overlap with the last one
                    boolean overlaps = subslotsOfThisSlot.stream().anyMatch(s -> !s.getEnd().isBefore(lastSubStart));
                    if (!overlaps && !lastSubEnd.isAfter(slotEnd)) {
                        TimeSlot subslot = new TimeSlot(lastSubStart, lastSubEnd);
                        int score = scoreSlot(subslot, preferences);
                        allSubslots.add(new ScoredSubslot(subslot, date, score));
                    }
                }
            }
        }

        // 3. Sort subslots by score and preference
        allSubslots.sort((a, b) -> {
            int scoreDiff = b.score - a.score;
            if (scoreDiff != 0) return scoreDiff;
            if (!preferences.isEmpty()) {
                UserStudyPreference.PreferenceType topPref = preferences.get(0).getPreferenceType();
                int prefHour = switch (topPref) {
                    case MORNING -> 8;
                    case AFTERNOON -> 14;
                    case EVENING -> 19;
                    case NIGHT -> 25;
                };
                int aDist = Math.abs(a.slot.getStart().getHour() - prefHour);
                int bDist = Math.abs(b.slot.getStart().getHour() - prefHour);
                if (aDist != bDist) return aDist - bDist;
            }
            // NEW: Sort by start time when score and preference proximity are equal (earliest date first)
            return a.slot.getStart().compareTo(b.slot.getStart());
        });

        // 4. Schedule sessions in the best subslots
        Set<LocalDateTime> usedTimes = new HashSet<>();
        for (ScoredSubslot sub : allSubslots) {
            if (minutesLeft <= 0) break;
            boolean overlaps = usedTimes.stream().anyMatch(t ->
                !(sub.slot.getEnd().isBefore(t) || sub.slot.getStart().isAfter(t.plusMinutes((long) maxSessionLength + pauseLength - 1)))
            );
            if (overlaps) {
                continue;
            }
            // Create session
            StudySession session = new StudySession();
            session.setId(UUID.randomUUID());
            session.setUserId(userId);
            session.setStartTime(sub.slot.getStart());
            session.setEndTime(sub.slot.getEnd());
            session.setCreatedOn(LocalDateTime.now());
            session.setToDoDescription(assignment.getTitle());
            session.setAssignmentId(assignment.getId());
            studySessionService.createStudySession(session);
            minutesLeft -= maxSessionLength;
            // Block this time period including pause
            usedTimes.add(sub.slot.getStart());
        }
        // 8. Save
        for (StudySession s : sessionsToSave) {
            studySessionService.createStudySession(s);
        }
    }

    // Helper class for TimeSlot (without timezone)
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

    // Subtract blockers from slots (simplified logic)
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

    // Slot scoring by preference
    private int scoreSlot(TimeSlot slot, List<UserStudyPreference> preferences) {
        // Note: A user can have a maximum of two preferences (e.g. MORNING and EVENING).
        // The score assignment is designed for this: 100 for top preference, 90 for second.
        // Time ranges for preferences
        Map<UserStudyPreference.PreferenceType, int[]> prefRanges = Map.of(
                UserStudyPreference.PreferenceType.MORNING, new int[]{5, 12},
                UserStudyPreference.PreferenceType.AFTERNOON, new int[]{12, 17},
                UserStudyPreference.PreferenceType.EVENING, new int[]{17, 23},
                UserStudyPreference.PreferenceType.NIGHT, new int[]{23, 29} // 29 = 5 AM next day
        );
        int slotStart = slot.getStart().getHour();
        int slotEnd = slot.getEnd().getHour();
        int bestScore = 0;
        for (int i = 0; i < preferences.size(); i++) {
            UserStudyPreference.PreferenceType type = preferences.get(i).getPreferenceType();
            int[] range = prefRanges.get(type);
            int overlap = Math.max(0, Math.min(slotEnd, range[1]) - Math.max(slotStart, range[0]));
            int slotLen = slotEnd - slotStart;
            double percent = slotLen > 0 ? (double) overlap / slotLen : 0;
            if (percent > 0.5) {
                bestScore = Math.max(bestScore, 100 - i * 10); // 100 for top preference, 90 for second, etc.
            } else if (percent > 0) {
                bestScore = Math.max(bestScore, 50 - i * 10); // 50 for partial overlap
            }
        }
        return bestScore;
    }

    // Helper method to load UserCreation by userId
    private UserCreation getUserById(UUID userId) {
        return jdbi.withExtension(UserCreationRepository.class, repo -> repo.getUserById(userId));
    }

    // Dummy enums and classes for preferences
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
