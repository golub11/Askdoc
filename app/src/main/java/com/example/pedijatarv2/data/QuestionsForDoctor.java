package com.example.pedijatarv2.data;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class QuestionsForDoctor {
    @Embedded
    public DoctorWithUsers doctors;

    @Relation(entity = Question.class,parentColumn = "doctor_id",entityColumn = "doctorId")
    public List<Question> questions;
}
