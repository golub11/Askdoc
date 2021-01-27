package com.example.pedijatarv2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface UserAccountDAO {

    @Insert
    public void insert(UserAccount userAccount);

    @Query("SELECT * FROM user WHERE user_id=:id")
    public UserAccount loadUserById(int id);

    @Query("SELECT * FROM user")
    LiveData<List<UserAccount>> getUsers();

    @Query("DELETE FROM user")
    public void deleteAllUsers();

    @Query("SELECT * FROM user WHERE user_name=:userName AND password=:pw")
    public UserAccount checkUser(String userName, String pw);

    @Query("SELECT * from user where user_id=:id")
    public UserAccount getUser(int id);

   /* @Query("UPDATE user set doctor_id =:doctor where user_id=:user")
    void connectUserWithDoctor(int user, long doctor);*/

    @Insert(onConflict = 1)
    void insertPairs(UserDoctorCrossRef userWithDoctor);

    @Transaction
    @Query("SELECT * FROM user WHERE user_id=:userId;")
    List<UserWithDoctors> getDoctorsByUser(int userId);


    @Query("SELECT * FROM user LIMIT 1;")
    int[] getAnyUser();

    @Transaction
    @Query("SELECT * FROM user WHERE user_id=:id;")
    LiveData<List<QuestionWithUsersAndDoctors>> getTriple(int id);
/*
    @Transaction
    @Query("SELECT * FROM user;")
    List<QuestionWithUsersAndDoctors> getTriple();
    */
}
