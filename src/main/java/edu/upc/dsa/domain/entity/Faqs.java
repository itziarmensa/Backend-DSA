package edu.upc.dsa.domain.entity;

public class Faqs {

    private String question;
    private String answer;

    public Faqs() {
    }

    public Faqs(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
