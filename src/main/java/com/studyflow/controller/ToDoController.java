package com.studyflow.controller;

import com.studyflow.auth.CurrentUser;
import com.studyflow.model.todo.ToDo;
import com.studyflow.service.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new ToDo")
    public ResponseEntity<String> createToDo(@RequestBody ToDo toDo, @CurrentUser String clerkUserId) {
        toDoService.createToDo(toDo, clerkUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body("ToDo successfully created.");
    }

    @PutMapping("/edit/{id}")
    @Operation(summary = "Update a ToDo")
    public ResponseEntity<String> updateToDo(@PathVariable("id") UUID id, @RequestBody ToDo toDo) {
        toDoService.updateToDo(id, toDo);
        return ResponseEntity.ok("ToDo successfully updated.");
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a ToDo")
    public ResponseEntity<String> deleteToDo(@PathVariable("id") UUID id) {
        toDoService.deleteToDo(id);
        return ResponseEntity.ok("ToDo successfully deleted.");
    }

    @GetMapping("/my")
    @Operation(summary = "Get all ToDos for the current user")
    public ResponseEntity<List<ToDo>> getToDosByCurrentUser(@CurrentUser String clerkUserId) {
        UUID userId = toDoService.getUserIdByClerkId(clerkUserId);
        List<ToDo> todos = toDoService.getToDosByUserId(userId);
        return ResponseEntity.ok(todos);
    }
} 