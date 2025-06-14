package com.studyflow.service;

import com.studyflow.model.course.Course;
import com.studyflow.repository.CourseRepository;
import com.studyflow.repository.UserCreationRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseService {

    private final Jdbi jdbi;

    public CourseService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createCourse(Course course, String clerkUserId) {
        try {
            UUID userId = getUserIdByClerkId(clerkUserId);
            course.setCreatedBy(userId);
            jdbi.useExtension(CourseRepository.class, repo -> repo.insertCourse(course));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Fehler beim Erstellen des Kurses", e);
        }
    }


    public void updateCourse(UUID id, Course course) {
        try {
            jdbi.useExtension(CourseRepository.class, repo -> {
                course.setId(id);
                repo.updateCourse(course);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCourse(UUID id) {
        try {
            jdbi.useExtension(CourseRepository.class, repo -> repo.deleteCourseById(id));
        } catch (Exception e) {
            System.err.println("Error while deleting course: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error while deleting course", e);
        }
    }

    public List<Course> getCoursesByUserId(UUID userId) {
        return jdbi.withExtension(CourseRepository.class, repo -> repo.findCoursesByUserId(userId));
    }

    public UUID getUserIdByClerkId(String clerkUserId) {
        return jdbi.withExtension(UserCreationRepository.class, repo ->
                repo.findUserIdByClerkUserId(clerkUserId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found for clerk ID: " + clerkUserId))
        );
    }




}
