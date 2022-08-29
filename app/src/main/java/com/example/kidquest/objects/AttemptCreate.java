package com.example.kidquest.objects;

public class AttemptCreate {
    Integer questId;
    Integer correctAnswersCount;

    public AttemptCreate(Integer questId, Integer correctAnswersCount) {
        this.questId = questId;
        this.correctAnswersCount = correctAnswersCount;
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
