package com.studyflow.repository;

import com.studyflow.model.user.UserCreation;
import com.studyflow.model.user.UserPreferences;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;

import java.util.Optional;
import java.util.UUID;

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
            'Europe/Berlin',
            :maxStudyDuration,
            :startLearningTime,
            :endLearningTime,
            :username,
            :blackoutWeekdays,
            :clerkUserId
        )
    """)
    void insertUser(@BindBean UserCreation user, @Bind("clerkUserId") String clerkUserId);


    @SqlQuery("""
    SELECT EXISTS (
        SELECT 1 FROM users WHERE clerk_user_id = :clerkUserId
    )
""")
    boolean existsByClerkUserId(@Bind("clerkUserId") String clerkUserId);

    @SqlQuery("""
    SELECT id FROM users WHERE clerk_user_id = :clerkUserId
""")
    Optional<UUID> findUserIdByClerkUserId(@Bind("clerkUserId") String clerkUserId);

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    @RegisterBeanMapper(UserCreation.class)
    UserCreation getUserById(@Bind("id") UUID id);

    @SqlQuery("""
        SELECT 
            max_study_duration,
            start_learning_time,
            end_learning_time,
            username,
            blackout_weekdays
        FROM users 
        WHERE clerk_user_id = :clerkUserId
    """)
    @RegisterBeanMapper(UserPreferences.class)
    Optional<UserPreferences> getUserPreferencesByClerkUserId(@Bind("clerkUserId") String clerkUserId);

    @SqlUpdate("""
        UPDATE users 
        SET 
            max_study_duration = COALESCE(:maxStudyDuration, max_study_duration),
            start_learning_time = COALESCE(:startLearningTime, start_learning_time),
            end_learning_time = COALESCE(:endLearningTime, end_learning_time),
            username = COALESCE(:username, username),
            blackout_weekdays = COALESCE(:blackoutWeekdays, blackout_weekdays)
        WHERE clerk_user_id = :clerkUserId
    """)
    void updateUserPreferences(@BindBean UserPreferences preferences, @Bind("clerkUserId") String clerkUserId);

}
