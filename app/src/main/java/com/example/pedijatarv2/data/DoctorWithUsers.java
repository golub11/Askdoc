package com.example.pedijatarv2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class DoctorWithUsers {

    @Embedded
    public Doctor doctor;

    @Relation(parentColumn = "doctor_id", entityColumn = "user_id", associateBy = @Junction(UserDoctorCrossRef.class))
    public List<UserAccount> users;
}
