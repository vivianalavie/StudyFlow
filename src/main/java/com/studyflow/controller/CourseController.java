package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.course.Course;
import com.studyflow.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course, @CurrentUser String clerkUserId) {
        course.setCreatedBy(null);
        courseService.createCourse(course, clerkUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully.");
    }


    @PutMapping("/edit/{id}")
    @Operation(summary = "Update an existing course by ID")
    public ResponseEntity<String> updateCourse(
            @Parameter(description = "The ID of the course to update", required = true)
            @PathVariable("id") UUID id,
            @RequestBody Course course
    ) {
        courseService.updateCourse(id, course);
        return ResponseEntity.ok("Course updated successfully.");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a course by ID")
    public ResponseEntity<String> deleteCourse(@PathVariable("id") UUID id) {
        courseService.deleteCourse(id);
        return ResponseEntity.ok("Course deleted successfully.");
    }

    @GetMapping("/my")
    @Operation(summary = "Get all courses created by the current (authenticated) user")
    public ResponseEntity<List<Course>> getCoursesByCurrentUser(@CurrentUser String clerkUserId) {
        UUID userId = courseService.getUserIdByClerkId(clerkUserId);
        List<Course> courses = courseService.getCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }



}

