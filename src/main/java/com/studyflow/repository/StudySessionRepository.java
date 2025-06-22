package com.studyflow.repository;

import com.studyflow.model.StudySession.StudySession;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterConstructorMapper(StudySession.class)
public interface StudySessionRepository {
    @SqlUpdate("""
        INSERT INTO study_sessions (
            id, user_id, start_time, end_time, created_on, to_do_description, assignment_id
        ) VALUES (
            :id, :userId, :startTime, :endTime, :createdOn, :toDoDescription, :assignmentId
        )
    """)
    void insertStudySession(@BindBean StudySession studySession);

    @SqlQuery("SELECT * FROM study_sessions WHERE user_id = :userId")
    List<StudySession> getStudySessionsByUser(@Bind("userId") UUID userId);

    @SqlQuery("SELECT * FROM study_sessions WHERE assignment_id = :assignmentId")
    List<StudySession> getStudySessionsByAssignment(@Bind("assignmentId") UUID assignmentId);
} 