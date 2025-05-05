package com.studyflow.controller;

import com.studyflow.model.course.Course;
import com.studyflow.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @Operation(summary =  "Create a new course as a user")
    @PostMapping
    public ResponseEntity<String> createCourse(@RequestBody Course course) {
        try {
            courseService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body("Course created successfully.");
        } catch (Exception e) {
            e.printStackTrace(); // <- zeigt dir den echten Fehler in der Konsole
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

}

