package com.example.kidquest.objects;

import java.util.List;

public class Answers {
    String status;
    List<Answer_assembly> answerVariants;

    public Answers(String status, List<Answer_assembly> answerVariants) {
        this.status = status;
        this.answerVariants = answerVariants;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Answer_assembly> getAnswerVariants() {
        return answerVariants;
    }

    public void setAnswerVariants(List<Answer_assembly> answerVariants) {
        this.answerVariants = answerVariants;
    }
}
