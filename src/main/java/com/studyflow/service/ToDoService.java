package com.studyflow.service;

import com.studyflow.model.todo.ToDo;
import com.studyflow.repository.ToDoRepository;
import com.studyflow.repository.UserCreationRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ToDoService {
    private final Jdbi jdbi;

    public ToDoService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public void createToDo(ToDo toDo, String clerkUserId) {
        UUID userId = getUserIdByClerkId(clerkUserId);
        toDo.setId(UUID.randomUUID());
        toDo.setUserId(userId);
        jdbi.useExtension(ToDoRepository.class, repo -> repo.insertToDo(toDo));
    }

    public void updateToDo(UUID id, ToDo toDo) {
        toDo.setId(id);
        jdbi.useExtension(ToDoRepository.class, repo -> repo.updateToDo(toDo));
    }

    public void deleteToDo(UUID id) {
        jdbi.useExtension(ToDoRepository.class, repo -> repo.deleteToDoById(id));
    }

    public List<ToDo> getToDosByUserId(UUID userId) {
        return jdbi.withExtension(ToDoRepository.class, repo -> repo.findToDosByUserId(userId));
    }

    public UUID getUserIdByClerkId(String clerkUserId) {
        return jdbi.withExtension(UserCreationRepository.class, repo ->
                repo.findUserIdByClerkUserId(clerkUserId)
                        .orElseThrow(() -> new IllegalArgumentException("User not found for clerk ID: " + clerkUserId))
        );
    }
} 