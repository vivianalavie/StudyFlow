package com.studyflow.repository;

import com.studyflow.model.course.Course;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.UUID;

public interface CourseRepository {

    @SqlUpdate("""
        INSERT INTO courses (
            created_by_user_id, name, description, start_date, end_date,
            professor_name, total_points, total_workload_hours, total_self_work_hours, color
        )
        VALUES (
            :createdBy, :name, :description, :startDate, :endDate,
            :professorName, :totalPoints, :totalWorkloadHours, :totalSelfWorkHours, :color
        )
    """)
    void insertCourse(@BindBean Course course);

    @SqlUpdate("""
    UPDATE courses SET
        name = :name,
        description = :description,
        start_date = :startDate,
        end_date = :endDate,
        professor_name = :professorName,
        total_points = :totalPoints,
        total_workload_hours = :totalWorkloadHours,
        total_self_work_hours = :totalSelfWorkHours,
        color = :color
    WHERE id = :id
    """)
    void updateCourse(@BindBean Course course);

    @SqlUpdate("DELETE FROM courses WHERE id = :id")
    void deleteCourseById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM courses WHERE created_by_user_id = :userId")
    @RegisterBeanMapper(Course.class)
    List<Course> findCoursesByUserId(@Bind("userId") UUID userId);

    @SqlQuery("SELECT * FROM courses WHERE id = :id")
    @RegisterBeanMapper(Course.class)
    Course getCourseById(@Bind("id") UUID id);

    @SqlQuery("SELECT created_by_user_id FROM courses WHERE id = :id")
    UUID getCreatedByUserIdByCourseId(@Bind("id") UUID id);

}
