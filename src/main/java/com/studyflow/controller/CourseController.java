package com.studyflow.controller;

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

    @Operation(summary =  "Create a new course as a user")
    @PostMapping("/create")
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        courseService.createCourse(course);
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

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all courses created by the current user")
    public ResponseEntity<List<Course>> getCoursesByUserId(@RequestParam("userId") UUID userId) {
        List<Course> courses = courseService.getCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }

}

