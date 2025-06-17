package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.timeblocker.TimeBlocker;
import com.studyflow.service.TimeBlockerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/timeblockers")
public class TimeBlockerController {

    private final TimeBlockerService timeBlockerService;

    public TimeBlockerController(TimeBlockerService timeBlockerService) {
        this.timeBlockerService = timeBlockerService;
    }

    @Operation(summary = "Create a new time blocker")
    @PostMapping("/create")
    public ResponseEntity<String> createTimeBlocker(@CurrentUser String clerkUserId,
                                                    @RequestBody TimeBlocker timeBlocker) {
        UUID userId = timeBlockerService.getUserIdByClerkId(clerkUserId);
        timeBlocker.setUserId(userId);
        timeBlockerService.createTimeBlocker(timeBlocker);
        return ResponseEntity.status(HttpStatus.CREATED).body("TimeBlocker created.");
    }


    @Operation(summary = "Update an existing time blocker")
    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateTimeBlocker(@PathVariable("id") UUID id, @RequestBody TimeBlocker timeBlocker) {
        timeBlockerService.updateTimeBlocker(id, timeBlocker);
        return ResponseEntity.ok("TimeBlocker updated.");
    }

    @Operation(summary = "Delete a time blocker")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTimeBlocker(@PathVariable("id") UUID id) {
        timeBlockerService.deleteTimeBlocker(id);
        return ResponseEntity.ok("TimeBlocker deleted.");
    }

    @Operation(summary = "Get all time blockers for a user")
    @GetMapping("/my")
    public ResponseEntity<List<TimeBlocker>> getTimeBlockers(@CurrentUser String clerkUserId) {
        UUID userId = timeBlockerService.getUserIdByClerkId(clerkUserId);
        List<TimeBlocker> blockers = timeBlockerService.getTimeBlockersByUser(userId);
        return ResponseEntity.ok(blockers);
    }
}
