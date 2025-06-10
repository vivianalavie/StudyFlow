package com.studyflow.model.user;

import java.time.LocalTime;
import java.util.List;

public class UserCreation {
    private String timezone;
    private Integer maxStudyDuration;
    private LocalTime startLearningTime;
    private LocalTime endLearningTime;
    private String username;
    private List<String> blackoutWeekdays;
    private String chronotype;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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
}