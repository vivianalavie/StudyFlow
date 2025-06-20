package com.studyflow.service;

import com.studyflow.model.calendar.CalendarEvent;
import com.studyflow.repository.CalendarRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CalendarService {

    private final Jdbi jdbi;

    public CalendarService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<CalendarEvent> getUserCalendarEvents(UUID userId) {
        return jdbi.withExtension(CalendarRepository.class, repo -> {
            List<CalendarEvent> result = new ArrayList<>();
            result.addAll(repo.findStudySessionEvents(userId));
            result.addAll(repo.findTimeBlockerEvents(userId));
            return result;
        });
    }
}