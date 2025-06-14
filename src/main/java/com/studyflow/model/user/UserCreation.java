package com.studyflow.model.user;

import java.time.LocalTime;
import java.util.List;

public class UserCreation {
//    private String timezone; We will firstly do it wihtout
    private Integer maxStudyDuration;
    private LocalTime startLearningTime;
    private LocalTime endLearningTime;
    private String username;
    private List<String> blackoutWeekdays;

//    public String getTimezone() {
//        return timezone;
//    }
//
//    public void setTimezone(String timezone) {
//        this.timezone = timezone;
//    }

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