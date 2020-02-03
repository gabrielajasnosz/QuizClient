package com.pdfgenerator.model;

import java.io.Serializable;
import java.util.Arrays;


public class AnswerData implements Serializable {

    private int questionId;
    private boolean lastQuestion;
    private Integer[] selectedAnswers;

    public AnswerData() {
    }


    public AnswerData(int questionId, boolean lastQuestion, Integer[] selectedAnswers) {
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

    public Integer[] getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setSelectedAnswersAnswers(Integer[] answers) {
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
                + ", \"questionId\":\"" + questionId + "\""
              + ", \"selectedAnswers\":" + Arrays.toString(selectedAnswers).replaceAll("0","") //TODO:?
                + ", \"lastQuestion\":\"" + lastQuestion + "\""
                + "}}";
    }
}
