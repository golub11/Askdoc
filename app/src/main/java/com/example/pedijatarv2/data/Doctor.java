package com.example.pedijatarv2.data;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="doctor_table")
public class Doctor implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="doctor_id")
    public long doctor_id;

    @ColumnInfo(name="first_name")
    private String firstName;

    @ColumnInfo(name="last_name")
    private String lastName;


    public Doctor(){}

    public Doctor(String fn, String ln){
        this.firstName= fn;
        this.lastName= ln;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @ColumnInfo(name = "sum_rating")
    public long sumRating = 0;

    @ColumnInfo(name = "num_of_votes")
    public int numOfVotes = 0;



}
