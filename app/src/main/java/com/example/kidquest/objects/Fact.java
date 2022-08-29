package com.example.kidquest.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fact {
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("fact")
    @Expose
    Fact_assembly fact;

    public Fact(String status, Fact_assembly fact) {
        this.status = status;
        this.fact = fact;
    }

    public void setFact_assemblies(Fact_assembly fact) {
        this.fact = fact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Fact_assembly getFact_assemblies() {
        return fact;
    }
}
