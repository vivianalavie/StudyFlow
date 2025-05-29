package com.studyflow.repository.scheduler;

import com.studyflow.model.scheduler.UserBlackout;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;

import java.util.List;
import java.util.UUID;

/**
 * Reads the days‐off a user selected in the survey.
 */
public interface BlackoutRepository {
    @SqlQuery("""
    SELECT date, weekday
      FROM user_blackout
     WHERE user_id = :uid
  """)
    @RegisterBeanMapper(UserBlackout.class)
    List<UserBlackout> findByUser(@Bind("uid") UUID userId);
}
