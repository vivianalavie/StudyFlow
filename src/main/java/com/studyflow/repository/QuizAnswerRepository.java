package com.studyflow.repository;

import com.studyflow.model.QuizAnswer.QuizAnswer;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;

@RegisterConstructorMapper(QuizAnswer.class)
public interface QuizAnswerRepository {

    @SqlUpdate("""
     INSERT INTO quiz_answers (
         id, question_id, choice, created_on, quiz_attempt_id
     )
     VALUES (
         :id, :questionId, :choice, :createdOn, :quizAttemptId
     )
 """)
    void insertQuizAnswer(@BindBean QuizAnswer answer);

}