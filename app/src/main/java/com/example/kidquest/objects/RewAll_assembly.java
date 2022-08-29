package com.example.kidquest.objects;

public class RewAll_assembly {
    Integer id;
    String name;
    String description;
    Integer previewId;
    Integer questId;

    public RewAll_assembly(Integer id, String name, String description, Integer previewId, Integer questId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.previewId = previewId;
        this.questId = questId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPreviewId() {
        return previewId;
    }

    public void setPreviewId(Integer previewId) {
        this.previewId = previewId;
    }

    public Integer getQuestId() {
        return questId;
    }

    public void setQuestId(Integer questId) {
        this.questId = questId;
    }
}
