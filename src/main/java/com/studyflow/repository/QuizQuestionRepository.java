package com.studyflow.repository;

import com.studyflow.model.QuizQuestion;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;

import java.util.List;

@RegisterConstructorMapper(QuizQuestion.class)
public interface QuizQuestionRepository {

    @SqlQuery("SELECT * FROM quiz_questions WHERE active = true")
    List<QuizQuestion> getActiveQuizQuestions();
}

