package com.example.kidquest.objects;

import java.util.List;

public class UserStat {
    String status;
    List<AttemptsSt> attempts;

    public UserStat(String status, List<AttemptsSt> attempts) {
        this.status = status;
        this.attempts = attempts;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AttemptsSt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<AttemptsSt> attempts) {
        this.attempts = attempts;
    }
}
