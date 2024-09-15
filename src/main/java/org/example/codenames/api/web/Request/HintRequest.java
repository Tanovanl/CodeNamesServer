package org.example.codenames.api.web.Request;

public class HintRequest {
    private String playerName;
    private String hintWord;
    private int hintNumber;

    public HintRequest() {
    }

    public HintRequest(String playerName, String hintWord, int hintNumber) {
        this.playerName = playerName;
        this.hintWord = hintWord;
        this.hintNumber = hintNumber;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
