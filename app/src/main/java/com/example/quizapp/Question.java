package com.example.quizapp;

import java.util.List;

public class Question {

    private String questionText;
    private List<String> options;
    private int correctAnswerIndex;

    // Constructor to create questions
    public Question(String questionText, List<String> options, int correctAnswerIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

}
