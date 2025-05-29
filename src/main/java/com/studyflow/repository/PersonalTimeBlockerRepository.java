package com.studyflow.repository;

import com.studyflow.model.PersonalTimeBlocker;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.OffsetDateTime;
import java.util.UUID;

@RegisterConstructorMapper(PersonalTimeBlocker.class)
public interface PersonalTimeBlockerRepository {

    @SqlUpdate("""
        INSERT INTO time_blocker (
            name, description, start_date, end_date, user_id
        )
        VALUES (
            :name, :description, :start_date, :end_date, :user_id
        )
    """)
    void createTimeBlocker(
        @Bind("name") String name,
        @Bind("description") String description,
        @Bind("start_date") OffsetDateTime startDate,
        @Bind("end_date") OffsetDateTime endDate,
        @Bind("user_id") UUID userId
    );
}
