package com.studyflow.repository;

import com.studyflow.model.course.Course;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import java.util.UUID;




@RegisterConstructorMapper(Course.class)
public interface CourseRepository {

    @SqlUpdate("""
        INSERT INTO courses (
            id, created_by, name, description, start_date, end_date,
            professor_name, total_points, total_workload_hours, total_self_work_hours
        )
        VALUES (
            :id, :createdBy, :name, :description, :startDate, :endDate,
            :professorName, :totalPoints, :totalWorkloadHours, :totalSelfWorkHours
        )
    """)
    void insertCourse(@BindBean Course course);
}
