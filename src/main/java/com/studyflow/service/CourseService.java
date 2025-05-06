package com.studyflow.service;

import com.studyflow.model.course.Course;
import com.studyflow.repository.CourseRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourseService {

    private final Jdbi jdbi;

    public CourseService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createCourse(Course course) {
        try {
            jdbi.useExtension(CourseRepository.class, repo -> repo.insertCourse(course));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
