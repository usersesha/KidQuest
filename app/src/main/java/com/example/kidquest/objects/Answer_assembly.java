package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer_assembly {

    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("questionId")
    @Expose
    Integer questionId;
    @SerializedName("previewId")
    @Expose
    Integer previewId;
    @SerializedName("text")
    @Expose
    String answer;
    @SerializedName("isRight")
    @Expose
    Boolean relev;

    public Answer_assembly(Integer id, Integer questionId, Integer previewId, String answer, Boolean relev) {
        this.id = id;
        this.questionId = questionId;
        this.previewId = previewId;
        this.answer = answer;
        this.relev = relev;
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

    public Integer getPreviewId() {
        return previewId;
    }

    public void setPreviewId(Integer previewId) {
        this.previewId = previewId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getRelev() {
        return relev;
    }

    public void setRelev(Boolean relev) {
        this.relev = relev;
    }
}
