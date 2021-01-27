package com.example.pedijatarv2.data;

import androidx.room.Embedded;
import androidx.room.Entity;

@Entity(primaryKeys = {"user_id", "doctor_id"})
public class UserDoctorCrossRef {
    public int user_id;
   public long doctor_id;

    public UserDoctorCrossRef(int user_id, long doctor_id) {
        this.user_id = user_id;
        this.doctor_id = doctor_id;
        //this.question = question;
    }
}
