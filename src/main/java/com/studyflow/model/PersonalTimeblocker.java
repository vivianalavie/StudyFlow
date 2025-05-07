package com.studyflow.model;
import java.time.OffsetDateTime;
import java.util.UUID;

public class PersonalTimeblocker {

    private UUID id;
    private UUID userId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private String title;
    private String description;
    private String location;
    private String occurrence;
    private UUID categoryId;
    private UUID notificationId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

}
