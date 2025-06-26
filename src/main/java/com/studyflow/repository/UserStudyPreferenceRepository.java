package com.studyflow.repository;

import com.studyflow.model.user.UserStudyPreference;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterConstructorMapper(UserStudyPreference.class)
public interface UserStudyPreferenceRepository {
    @SqlUpdate("""
        INSERT INTO user_study_preferences (
            id, user_id, preference_type, priority, created_at
        ) VALUES (
            :id, :userId, :preferenceType::study_time_preference, :priority, :createdAt
        )
    """)
    void insertUserStudyPreference(@BindBean UserStudyPreference preference);

    @SqlQuery("SELECT * FROM user_study_preferences WHERE user_id = :userId ORDER BY priority ASC")
    List<UserStudyPreference> getPreferencesByUser(@Bind("userId") UUID userId);

    @SqlUpdate("DELETE FROM user_study_preferences WHERE user_id = :userId")
    void deletePreferencesByUser(@Bind("userId") UUID userId);
} 
