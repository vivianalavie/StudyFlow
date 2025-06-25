package com.studyflow.model.user;

import java.time.LocalDateTime;
import java.util.UUID;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

public class UserStudyPreference {
    private UUID id;
    private UUID userId;
    private PreferenceType preferenceType;
    private int priority;
    private LocalDateTime createdAt;

    public enum PreferenceType {
        MORNING, AFTERNOON, EVENING, NIGHT
    }

    public UserStudyPreference() {}
    
    @JdbiConstructor
    public UserStudyPreference(UUID id, UUID userId, PreferenceType preferenceType, int priority, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.preferenceType = preferenceType;
        this.priority = priority;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public PreferenceType getPreferenceType() { return preferenceType; }
    public void setPreferenceType(PreferenceType preferenceType) { this.preferenceType = preferenceType; }

    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 