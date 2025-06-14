package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.assignment.Assignment;
import com.studyflow.service.AssignmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @Operation(summary = "Create a new assignment")
    @PostMapping("/create")
    public ResponseEntity<String> createAssignment(@RequestBody Assignment assignment) {
        assignmentService.createAssignment(assignment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Assignment created.");
    }

    @Operation(summary = "Update an existing assignment")
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateAssignment(@PathVariable("id") UUID id, @RequestBody Assignment assignment) {
        assignmentService.updateAssignment(id, assignment);
        return ResponseEntity.ok("Assignment updated.");
    }

    @Operation(summary = "Delete an existing assignment")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAssignment(@PathVariable("id") UUID id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok("Assignment deleted.");
    }

    @Operation(summary = "Get all assignments for a user")
    @GetMapping("/my")
    public ResponseEntity<List<Assignment>> getAssignmentsByUser(@CurrentUser String clerkUserId) {
        UUID userId = assignmentService.getUserIdByClerkId(clerkUserId);
        List<Assignment> assignments = assignmentService.getAssignmentsByUser(userId);
        return ResponseEntity.ok(assignments);
    }

}
