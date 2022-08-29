package com.example.kidquest.objects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question_assembly {
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("questId")
    @Expose
    Integer questId;
    @SerializedName("number")
    @Expose
    Integer number;
    @SerializedName("typeId")
    @Expose
    Integer typeId;
    @SerializedName("previewId")
    @Expose
    Integer previewId;
    @SerializedName("text")
    @Expose
    String  text;

    public Question_assembly(int id, int questId, String text, Integer previewId, int number, int typeId) {
        this.id = id;
        this.questId = questId;
        this.previewId = previewId;
        this.number = number;
        this.typeId = typeId;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getPreviewId() {
        return previewId;
    }

    public void setPreviewId(Integer previewId) {
        this.previewId = previewId;
    }

    public String getQuestion() {
        return text;
    }

    public void setQuestion(String text) {
        this.text = text;
    }
}
