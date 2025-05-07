package com.studyflow.model.assignment;

import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Assignment {
    private UUID id;
    private String title;
    private String description;
    private UUID courseId;
    private int totalAchievablePoints;
    private OffsetDateTime deadline;
    private Difficulty difficulty;

    public Assignment() {
    }

    @JdbiConstructor
    public Assignment(UUID id, String title, String description, UUID courseId,
                      int totalAchievablePoints, OffsetDateTime deadline, Difficulty difficulty) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.totalAchievablePoints = totalAchievablePoints;
        this.deadline = deadline;
        this.difficulty = difficulty;
    }

    // Getter und Setter

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public int getTotalAchievablePoints() {
        return totalAchievablePoints;
    }

    public void setTotalAchievablePoints(int totalAchievablePoints) {
        this.totalAchievablePoints = totalAchievablePoints;
    }

    public OffsetDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(OffsetDateTime deadline) {
        this.deadline = deadline;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}
