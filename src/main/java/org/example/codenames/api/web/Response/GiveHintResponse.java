package org.example.codenames.api.web.Response;

public class GiveHintResponse {
    private String hintWord;
    private int hintNumber;

    public GiveHintResponse() {
    }

    public GiveHintResponse(String hintWord, int hintNumber) {
        this.hintWord = hintWord;
        this.hintNumber = hintNumber;
    }

    public String getHintWord() {
        return hintWord;
    }

    public void setHintWord(String hintWord) {
        this.hintWord = hintWord;
    }

    public int getHintNumber() {
        return hintNumber;
    }

    public void setHintNumber(int hintNumber) {
        this.hintNumber = hintNumber;
    }
}
