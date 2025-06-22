package com.studyflow.controller;

import com.studyflow.service.SchedulerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {
    private final SchedulerService schedulerService;

    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @PostMapping("/generate/{assignmentId}")
    public ResponseEntity<String> generateSchedule(@PathVariable UUID assignmentId) {
        schedulerService.generateScheduleForAssignment(assignmentId);
        return ResponseEntity.ok("Scheduler was generated.");
    }
} 