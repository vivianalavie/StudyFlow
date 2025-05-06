package com.studyflow.model.survey;

import java.time.OffsetDateTime;
import java.util.UUID;

public class QuizAnswer {

    private UUID id;
    private UUID questionId;
    private String choice;
    private OffsetDateTime createdOn;
    private UUID quizAttemptId;

    // Standard-Konstruktor
    public QuizAnswer() {}

    // Getter und Setter
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public OffsetDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(OffsetDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public UUID getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(UUID quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }
}
