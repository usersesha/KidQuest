package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestsAr {
    int id;
    @SerializedName("previewId")
    @Expose
    int img;
    @SerializedName("name")
    @Expose
    String title;
    @SerializedName("description")
    @Expose
    String text;
    @SerializedName("questionsCount")
    @Expose
    int count;

    int categoryId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getImgQ() {
        return img;
    }

    public void setImgQ(int img) {
        this.img = img;
    }

    public void setTitleQ(String title) {
        this.title = title;
    }

    public void setTextQ(String text) {
        this.text = text;
    }

    public String getTitleQ() {
        return title;
    }

    public String getTextQ() {
        return text;
    }
}
