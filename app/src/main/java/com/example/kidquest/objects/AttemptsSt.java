package com.example.kidquest.objects;

public class AttemptsSt {
    Integer id;
    Integer userId;
    Integer questId;
    Integer correctAnswersCount;

    public AttemptsSt(Integer id, Integer userId, Integer questId, Integer correctAnswersCount) {
        this.id = id;
        this.userId = userId;
        this.questId = questId;
        this.correctAnswersCount = correctAnswersCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }

    public Integer getCorrectAnswersCount() {
        return correctAnswersCount;
    }

    public void setCorrectAnswersCount(Integer correctAnswersCount) {
        this.correctAnswersCount = correctAnswersCount;
    }
}
