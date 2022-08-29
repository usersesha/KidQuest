package com.example.kidquest.objects;


public class Quest_item {

    String token;
    String status;
    private QuestsAr[] quests;

    public QuestsAr[] getQuestsAr() {
        return quests;
    }

    public void setQuestsAr(QuestsAr[] quests) {
        this.quests = quests;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Quest_item(String token) {
        this.token = token;
    }

}
