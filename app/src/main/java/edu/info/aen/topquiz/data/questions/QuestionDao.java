package edu.info.aen.topquiz.data.questions;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface QuestionDao {
    @Query("SELECT * FROM question")
    LiveData<List<Question>> getAllQuestions();

    @Query("SELECT * FROM question WHERE question_id = :question_id")
    LiveData<Question> getQuestionById(long question_id);

    @Query("SELECT * FROM question WHERE question_id NOT IN (:seen_questions_id)")
    LiveData<List<Question>> getQuestionsNotSeen(long[] seen_questions_id);

    @Insert
    long insertOneQuestion(Question question);

    @Insert
    long[] insertManyQuestion(Question... questions);

    @Delete
    void deleteManyQuestions(Question... questions);

    @Delete
    void deleteOneQuestion(Question question);

    @Query("DELETE FROM question")
    void deleteAllQuestions();
}