package com.example.kidquest.objects;

import java.util.List;

public class RewardUser {

    String status;
    List<RewAll_assembly>  userAwards;

    public RewardUser(String status, List<RewAll_assembly> userAwards) {
        this.status = status;
        this.userAwards = userAwards;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RewAll_assembly> getUserAwards() {
        return userAwards;
    }

    public void setUserAwards(List<RewAll_assembly> userAwards) {
        this.userAwards = userAwards;
    }
}
