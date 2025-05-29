package com.studyflow.model.scheduler;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.util.Optional;

/**
 * A day‐level blackout: either a specific date or a weekday.
 */
public class UserBlackout {
    private final Optional<LocalDate> date;
    private final Optional<DayOfWeek> weekday;

    public UserBlackout(Optional<LocalDate> date,
                        Optional<DayOfWeek> weekday) {
        this.date = date;
        this.weekday = weekday;
    }

    public Optional<LocalDate> getDate()   { return date; }
    public Optional<DayOfWeek> getWeekday(){ return weekday; }

    /** True if this blackout blocks the given date. */
    public boolean blocksDate(LocalDate d) {
        if (date.map(d::equals).orElse(false)) return true;
        if (weekday.map(w -> d.getDayOfWeek() == w).orElse(false))
            return true;
        return false;
    }
}
