package com.studyflow.repository;

import com.studyflow.model.PersonalTimeblocker;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterConstructorMapper(PersonalTimeblocker.class)
public interface PersonalTimeblockerRepository {

    @SqlUpdate("""
        INSERT INTO personal_timeblocker (
            id, user_id, start_date, end_date, title,
            description, location, occurrence, category_id, notification_id
        )
        VALUES (
            :id, :userId, :startDate, :endDate, :title,
            :description, :location, :occurrence, :categoryId, :notificationId
        )
    """)
    void insertTimeblock(@BindBean PersonalTimeblocker block);
}
