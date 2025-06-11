package com.studyflow.repository;

import com.studyflow.model.user.UserCreation;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

@RegisterConstructorMapper(UserCreation.class)
public interface UserCreationRepository {

    @SqlUpdate("""
        INSERT INTO users (
            timezone,
            max_study_duration,
            start_learning_time,
            end_learning_time,
            username,
            blackout_weekdays,
            clerk_user_id
        )
        VALUES (
            "Europa/Berlin",
            :maxStudyDuration,
            :startLearningTime,
            :endLearningTime,
            :username,
            :blackoutWeekdays,
            :clerkUserId
        )
    """)
    void insertUser(@BindBean UserCreation user, @Bind("clerkUserId") String clerkUserId);
}
