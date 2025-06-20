package com.studyflow.model.studysession;

import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public class StudySession {

    private UUID id;
    private UUID userId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdOn;
    private String toDoDescription;
    private UUID assignmentId;

    public StudySession() {}

    @JdbiConstructor
    public StudySession(UUID id, UUID userId, LocalDateTime startTime, LocalDateTime endTime,
                        LocalDateTime createdOn, String toDoDescription, UUID assignmentId) {
        this.id = id;
        this.userId = userId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createdOn = createdOn;
        this.toDoDescription = toDoDescription;
        this.assignmentId = assignmentId;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }

    public String getToDoDescription() { return toDoDescription; }
    public void setToDoDescription(String toDoDescription) { this.toDoDescription = toDoDescription; }

    public UUID getAssignmentId() { return assignmentId; }
    public void setAssignmentId(UUID assignmentId) { this.assignmentId = assignmentId; }
}
