package com.example.pedijatarv2.data;

import androidx.lifecycle.LiveData;
import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class QuestionWithUsersAndDoctors  {
    @Embedded
    public UserWithDoctors users;

    @Relation(entity=Question.class,parentColumn = "user_id",entityColumn = "userId")
    public List<Question> questions;
}
