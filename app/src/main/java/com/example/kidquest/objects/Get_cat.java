package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Get_cat {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("questCategory")
    @Expose
    private QuestCategory questCategory;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public QuestCategory getQuestCategory() {
        return questCategory;
    }

    public void setQuestCategory(QuestCategory questCategory) {
        this.questCategory = questCategory;
    }

    public Get_cat withQuestCategory(QuestCategory questCategory) {
        this.questCategory = questCategory;
        return this;
    }
}

