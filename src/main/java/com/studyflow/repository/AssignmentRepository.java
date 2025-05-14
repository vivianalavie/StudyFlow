package com.studyflow.repository;

import com.studyflow.model.assignment.Assignment;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.UUID;

@RegisterConstructorMapper(Assignment.class)
public interface AssignmentRepository {

    @SqlUpdate("""
    INSERT INTO assignments (
        id, title, description, course_id, total_achievable_points, deadline, difficulty
    )
    VALUES (
        :id, :title, :description, :courseId, :totalAchievablePoints, :deadline, :difficulty::difficulty
    )
    """)
    void insertAssignment(@BindBean Assignment assignment);


    @SqlUpdate("""
        UPDATE assignments SET
            title = :title,
            description = :description,
            course_id = :courseId,
            total_achievable_points = :totalAchievablePoints,
            deadline = :deadline,
            difficulty = :difficulty::difficulty
        WHERE id = :id
    """)
    void updateAssignment(@BindBean Assignment assignment);

    @SqlUpdate("DELETE FROM assignments WHERE id = :id")
    void deleteAssignment(@Bind("id") UUID id);

    @SqlQuery("""
    SELECT * FROM assignments 
    WHERE course_id IN (
        SELECT id FROM courses WHERE created_by = :userId
    )
""")
    @RegisterConstructorMapper(Assignment.class)
    List<Assignment> getAssignmentsByUserId(@Bind("userId") UUID userId);


}
