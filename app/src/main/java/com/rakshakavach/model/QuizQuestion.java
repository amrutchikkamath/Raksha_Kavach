package com.rakshakavach.model;

public class QuizQuestion {
    private String question;
    private String[] options;
    private int correctIndex;
    private String explanation;

    public QuizQuestion(String question, String[] options, int correctIndex, String explanation) {
        this.question = question;
        this.options = options;
        this.correctIndex = correctIndex;
        this.explanation = explanation;
    }

    public String getQuestion() { return question; }
    public String[] getOptions() { return options; }
    public int getCorrectIndex() { return correctIndex; }
    public String getExplanation() { return explanation; }
}
