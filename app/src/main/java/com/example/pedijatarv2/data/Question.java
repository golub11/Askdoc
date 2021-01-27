package com.example.pedijatarv2.data;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "questions")
public class Question implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int questionId;

    /*@Embedded
    public UserDoctorCrossRef usersAndDoctorsCrossRef;

    */
    private int userId;

    @ColumnInfo(defaultValue = "")
    public String answer;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDoctorId(long doctorId) {
        this.doctorId = doctorId;
    }

    public int getUserId() {
        return userId;
    }

    public long getDoctorId() {
        return doctorId;
    }

    private long doctorId;

    @Ignore
    public Question() {
    }

    public String question;

    public Question(String question) {
        this.question=question;
    }

    //public Date date;
}
