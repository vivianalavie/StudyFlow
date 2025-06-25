package com.studyflow.model.assignment;

public enum Difficulty {
    VERY_EASY(0.9),
    EASY(0.95),
    NORMAL(1.0),
    DIFFICULT(1.05),
    VERY_DIFFICULT(1.1);

    private final double multiplier;

    Difficulty(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }
}