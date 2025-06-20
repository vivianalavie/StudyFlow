package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.calendar.CalendarEvent;
import com.studyflow.service.CalendarService;
import com.studyflow.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final CourseService courseService;

    public CalendarController(CalendarService calendarService, CourseService courseService) {
        this.calendarService = calendarService;
        this.courseService = courseService;
    }

    @GetMapping("/events")
    @Operation(summary = "Get all calendar events for the current user")
    public ResponseEntity<List<CalendarEvent>> getCalendarEvents(@CurrentUser String clerkUserId) {
        UUID userId = courseService.getUserIdByClerkId(clerkUserId);
        List<CalendarEvent> events = calendarService.getUserCalendarEvents(userId);
        return ResponseEntity.ok(events);
    }
}