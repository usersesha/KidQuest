package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPass {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private String status;

    public ResetPass(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
