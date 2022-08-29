package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Questions {
    @SerializedName("status")
    @Expose
String status;
    @SerializedName("questions")
    @Expose
private List<Question_assembly> questions;

    public Questions(String status, List<Question_assembly> questions) {
        this.status = status;
        this.questions = questions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Question_assembly> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question_assembly> questions) {
        this.questions = questions;
    }
}
