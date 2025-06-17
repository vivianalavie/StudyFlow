package com.studyflow.model.timeblocker;

import com.studyflow.model.scheduler.Occurrence;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TimeBlocker {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private UUID userId;
    private Occurrence occurrence;

    public TimeBlocker() {}

    @JdbiConstructor
    public TimeBlocker(UUID id, String name, String description,
                       LocalDateTime startDate, LocalDateTime endDate,
                       UUID userId, Occurrence occurrence) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.userId = userId;
        this.occurrence = occurrence;
    }

    // Getter & Setter

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public Occurrence getOccurrence() { return occurrence; }
    public void setOccurrence(Occurrence occurrence) { this.occurrence = occurrence; }
}
