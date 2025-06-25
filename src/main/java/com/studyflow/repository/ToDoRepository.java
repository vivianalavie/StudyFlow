package com.studyflow.repository;

import com.studyflow.model.todo.ToDo;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;
import java.util.UUID;

@RegisterBeanMapper(ToDo.class)
public interface ToDoRepository {
    @SqlUpdate("""
        INSERT INTO todos (id, user_id, text)
        VALUES (:id, :userId, :text)
    """)
    void insertToDo(@BindBean ToDo toDo);

    @SqlUpdate("""
        UPDATE todos SET text = :text WHERE id = :id
    """)
    void updateToDo(@BindBean ToDo toDo);

    @SqlUpdate("DELETE FROM todos WHERE id = :id")
    void deleteToDoById(@Bind("id") UUID id);

    @SqlQuery("SELECT * FROM todos WHERE user_id = :userId")
    List<ToDo> findToDosByUserId(@Bind("userId") UUID userId);
} 