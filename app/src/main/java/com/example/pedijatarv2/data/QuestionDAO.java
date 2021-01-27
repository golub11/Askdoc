package com.example.pedijatarv2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;



@Dao
public interface QuestionDAO {

    @Insert
    public void insert(Question q);

   /* @Transaction
    @Query("SELECT * from questions")
    public List<QuestionWithUsersAndDoctors> getQuestionsWithUsersAndDoctors();
*/
    @Query("UPDATE questions SET answer=:a WHERE question=:q")
    void setAnswer(String a, String q);


    @Query("SELECT * FROM questions LIMIT 1")
    public Question[] getAnyQuestion();

    @Query("SELECT * FROM questions")
    public List<Question> getAllQuestions();
}
