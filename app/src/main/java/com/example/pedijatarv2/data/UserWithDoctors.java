package com.example.pedijatarv2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.pedijatarv2.data.Doctor;
import com.example.pedijatarv2.data.UserAccount;
import com.example.pedijatarv2.data.UserDoctorCrossRef;

import java.util.List;

public class UserWithDoctors {

    @Embedded
    public UserAccount user;

    @Relation(parentColumn = "user_id",entityColumn = "doctor_id",associateBy = @Junction(UserDoctorCrossRef.class))
    public List<Doctor> doctors;


}
