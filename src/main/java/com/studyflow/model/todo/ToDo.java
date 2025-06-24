package com.studyflow.model.todo;

import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;
import java.util.UUID;

public class ToDo {
    private UUID id;
    private UUID userId;
    private String text;

    public ToDo() {}

    @JdbiConstructor
    public ToDo(UUID id, UUID userId, String text) {
        this.id = id;
        this.userId = userId;
        this.text = text;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
} 