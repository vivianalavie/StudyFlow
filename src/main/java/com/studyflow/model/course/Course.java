package com.studyflow.model.course;

import java.time.LocalDate;
import java.util.UUID;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

public class Course {

    private UUID id;
    private UUID createdBy;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private String professorName;
    private int totalPoints;
    private int totalWorkloadHours;
    private int totalSelfWorkHours;
    private String color;

    public Course() {
    }

    // Complete Constructor
    @JdbiConstructor
    public Course(UUID id, UUID createdBy, String name, String description, LocalDate startDate,
                  LocalDate endDate, String professorName, int totalPoints,
                  int totalWorkloadHours, int totalSelfWorkHours, String color) {
        this.id = id;
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.professorName = professorName;
        this.totalPoints = totalPoints;
        this.totalWorkloadHours = totalWorkloadHours;
        this.totalSelfWorkHours = totalSelfWorkHours;
        this.color = color;
    }

    // Getter and Setter

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCreatedBy() { return createdBy; }
    public void setCreatedBy(UUID createdBy) { this.createdBy = createdBy; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }

    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }

    public int getTotalWorkloadHours() { return totalWorkloadHours; }
    public void setTotalWorkloadHours(int totalWorkloadHours) { this.totalWorkloadHours = totalWorkloadHours; }

    public int getTotalSelfWorkHours() { return totalSelfWorkHours; }
    public void setTotalSelfWorkHours(int totalSelfWorkHours) { this.totalSelfWorkHours = totalSelfWorkHours; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
