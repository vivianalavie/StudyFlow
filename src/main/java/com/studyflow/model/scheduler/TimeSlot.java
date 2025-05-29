package com.studyflow.model.scheduler;

import java.time.ZonedDateTime;
import java.time.Duration;

/**
 * A simple [start,end) interval.
 */
public class TimeSlot {
    private final ZonedDateTime start;
    private final ZonedDateTime end;

    public TimeSlot(ZonedDateTime start, ZonedDateTime end) {
        this.start = start;
        this.end = end;
    }

    public ZonedDateTime getStart() { return start; }
    public ZonedDateTime getEnd()   { return end; }

    /** Length in whole minutes. */
    public long lengthInMinutes() {
        return Duration.between(start, end).toMinutes();
    }

    /** Length in fractional hours. */
    public double lengthInHours() {
        return lengthInMinutes() / 60.0;
    }
}
