package com.pdfgenerator.model;

import java.io.Serializable;
import java.util.Arrays;


public class AnswerData implements Serializable {

    private int questionId;
    private boolean lastQuestion;
    private String selectedAnswers;

    public AnswerData() {
    }


    public AnswerData(int questionId, boolean lastQuestion, String selectedAnswers) {
        this.questionId=questionId;
        this.lastQuestion = lastQuestion;
        this.selectedAnswers = selectedAnswers;
    }

    public boolean getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(boolean lastQuestion) {
        this.lastQuestion = lastQuestion;
    }

    public String getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswers(String answers) {
        this.selectedAnswers = selectedAnswers;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId=questionId;
    }

    @Override
    public String toString() {
        return "{\"AnswerData\":{"
                + "\"questionId\":\"" + questionId + "\""
                + ", \"lastQuestion\":\"" + lastQuestion + "\""
                + ", \"selectedAnswers\":\"" + selectedAnswers + "\""
                + "}}";
    }
}
