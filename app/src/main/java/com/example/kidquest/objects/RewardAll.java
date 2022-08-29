package com.example.kidquest.objects;

import java.util.List;

public class RewardAll {
    String status;
List<RewAll_assembly> awardTypes;

    public RewardAll(String status, List<RewAll_assembly> awardTypes) {
        this.status = status;
        this.awardTypes = awardTypes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RewAll_assembly> getAwardTypes() {
        return awardTypes;
    }

    public void setAwardTypes(List<RewAll_assembly> awardTypes) {
        this.awardTypes = awardTypes;
    }
}
