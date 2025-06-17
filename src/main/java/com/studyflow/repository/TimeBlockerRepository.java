package com.studyflow.repository;

import com.studyflow.model.timeblocker.TimeBlocker;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterConstructorMapper(TimeBlocker.class)
public interface TimeBlockerRepository {

    @SqlUpdate("""
        INSERT INTO time_blocker (
            id, name, description, start_date, end_date, user_id, occurrence
        ) VALUES (
            :id, :name, :description, :startDate, :endDate, :userId, :occurrence::occurrence
        )
    """)
    void insertTimeBlocker(@BindBean TimeBlocker timeBlocker);

    @SqlUpdate("""
        UPDATE time_blocker SET
            name = :name,
            description = :description,
            start_date = :startDate,
            end_date = :endDate,
            occurrence = :occurrence::occurrence
        WHERE id = :id
    """)
    void updateTimeBlocker(@BindBean TimeBlocker timeBlocker);

    @SqlUpdate("DELETE FROM time_blocker WHERE id = :id")
    void deleteTimeBlocker(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM time_blocker WHERE user_id = :userId")
    List<TimeBlocker> getTimeBlockersByUser(@Bind("userId") UUID userId);
}
