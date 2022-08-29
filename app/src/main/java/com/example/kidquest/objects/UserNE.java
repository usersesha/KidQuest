package com.example.kidquest.objects;

public class UserNE {
    String status;
    UserInfo userInfo;

    public UserNE(String status, UserInfo userInfo) {
        this.status = status;
        this.userInfo = userInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
