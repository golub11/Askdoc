package com.example.pedijatarv2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DoctorDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Doctor doctor);

    @Transaction
    @Insert
    long insertDoctor(Doctor doctor);

    @Insert
    void insertUsers(List<UserAccount> users);

    @Query("DELETE FROM doctor_table")
    void deleteAll();

    @Query("SELECT * FROM doctor_table")
    LiveData<List<Doctor>> getAllDoctors();

    @Query("SELECT * FROM doctor_table LIMIT 1")
    Doctor[] getAnyDoctor();
/*
    @Transaction
    @Query("SELECT * FROM doctor_table WHERE doctor_id=:doctorId;")
    LiveData<List<DoctorWithUsers>> getUsersByDoctor(long doctorId);
*/
    @Delete
    void deleteDoctor(Doctor d);

    @Query("SELECT * FROM doctor_table WHERE doctor_id=:id AND first_name=:name")
    Doctor checkDoctor(String id, String name);

    @Transaction
    @Query("SELECT * FROM doctor_table WHERE doctor_id=:id;")
    LiveData<List<QuestionsForDoctor>> getTriple(long id);

    @Transaction
    @Query("SELECT * FROM doctor_table WHERE doctor_id=:userId;")
    List<DoctorWithUsers> getUsersByDoctor(long userId);

    @Query("SELECT * from doctor_table where doctor_id=:loggedDoctorId;")
    Doctor getDoctorById(long loggedDoctorId);

    @Query("update doctor_table set num_of_votes=num_of_votes+1, sum_rating=sum_rating+:rating where doctor_id=:id;")
    void increseVotesAndSum(long id, int rating);

    @Query("select sum_rating from doctor_table where doctor_id=:id;")
    long getSum(long id);

    @Query("select num_of_votes from doctor_table where doctor_id=:id;")
    int getNumOfVotes(long id);
}
