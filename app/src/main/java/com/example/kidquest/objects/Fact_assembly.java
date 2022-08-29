package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fact_assembly {
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("questionId")
    @Expose
    Integer questionId;
    @SerializedName("text")
    @Expose
    String text;
    @SerializedName("previewId")
    @Expose
    Integer previewId;

    public Fact_assembly(Integer id, Integer questionId, String text, Integer previewId) {
        this.id = id;
        this.questionId = questionId;
        this.text = text;
        this.previewId = previewId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getPreviewId() {
        return previewId;
    }

    public void setPreviewId(Integer previewId) {
        this.previewId = previewId;
    }
}
