package com.studyflow.repository;

import com.studyflow.model.calendar.CalendarEvent;
import com.studyflow.model.timeblocker.TimeBlocker;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.UUID;

public interface CalendarRepository {

    @SqlQuery("""
        SELECT a.title AS name, a.description AS description,
               ss.start_time AS startTime, ss.end_time AS endTime,
               'AUTOMATIC' AS type, c.color AS color
        FROM study_sessions ss
        JOIN assignments a ON ss.assignment_id = a.id
        JOIN courses c ON a.course_id = c.id
        WHERE ss.user_id = :userId
    """)
    @RegisterBeanMapper(CalendarEvent.class)
    List<CalendarEvent> findStudySessionEvents(@Bind("userId") UUID userId);

    @SqlQuery("""
        SELECT tb.name, tb.description,
               tb.start_date AS startTime, tb.end_date AS endTime,
               'PERSONAL' AS type, '#0047AB' AS color
        FROM time_blocker tb
        WHERE tb.user_id = :userId
    """)
    @RegisterBeanMapper(CalendarEvent.class)
    List<CalendarEvent> findTimeBlockerEvents(@Bind("userId") UUID userId);
}