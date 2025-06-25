package com.studyflow.model.user;

import java.time.LocalTime;
import java.util.List;

public class UserPreferences {
    private Integer maxStudyDuration;
    private LocalTime startLearningTime;
    private LocalTime endLearningTime;
    private String username;
    private List<String> blackoutWeekdays;

    public UserPreferences() {}

    public UserPreferences(Integer maxStudyDuration, LocalTime startLearningTime, 
                          LocalTime endLearningTime, String username, List<String> blackoutWeekdays) {
        this.maxStudyDuration = maxStudyDuration;
        this.startLearningTime = startLearningTime;
        this.endLearningTime = endLearningTime;
        this.username = username;
        this.blackoutWeekdays = blackoutWeekdays;
    }

    public Integer getMaxStudyDuration() {
        return maxStudyDuration;
    }

    public void setMaxStudyDuration(Integer maxStudyDuration) {
        this.maxStudyDuration = maxStudyDuration;
    }

    public LocalTime getStartLearningTime() {
        return startLearningTime;
    }

    public void setStartLearningTime(LocalTime startLearningTime) {
        this.startLearningTime = startLearningTime;
    }

    public LocalTime getEndLearningTime() {
        return endLearningTime;
    }

    public void setEndLearningTime(LocalTime endLearningTime) {
        this.endLearningTime = endLearningTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getBlackoutWeekdays() {
        return blackoutWeekdays;
    }

    public void setBlackoutWeekdays(List<String> blackoutWeekdays) {
        this.blackoutWeekdays = blackoutWeekdays;
    }
} 