package com.example.kidquest.objects;

public class GetCategory {
    int token;
    String status;
    private Category[] questCategories;

    public GetCategory(int token) {
        this.token = token;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Category[] getQuestCategories() {
        return questCategories;
    }

    public void setQuestCategories(Category[] questCategories) {
        this.questCategories = questCategories;
    }
}
