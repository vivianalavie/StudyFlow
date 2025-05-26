package com.studyflow.repository;

import com.studyflow.model.course.Course;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;

import java.util.List;
import java.util.UUID;




@RegisterConstructorMapper(Course.class)
public interface CourseRepository {

    @SqlUpdate("""
        INSERT INTO courses (
            created_by, name, description, start_date, end_date,
            professor_name, total_points, total_workload_hours, total_self_work_hours
        )
        VALUES (
            :createdBy, :name, :description, :startDate, :endDate,
            :professorName, :totalPoints, :totalWorkloadHours, :totalSelfWorkHours
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
        total_self_work_hours = :totalSelfWorkHours
    WHERE id = :id
""")
    void updateCourse(@BindBean Course course);

    @SqlUpdate("DELETE FROM courses WHERE id = :id")
    void deleteCourseById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM courses WHERE created_by_user_id = :userId")
    @RegisterBeanMapper(Course.class)
    List<Course> findCoursesByUserId(@Bind("userId") UUID userId);

}
