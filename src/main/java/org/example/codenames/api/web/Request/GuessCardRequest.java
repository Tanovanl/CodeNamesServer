package org.example.codenames.api.web.Request;

public class GuessCardRequest {
    private String playerName;
    private String cardName;


    public GuessCardRequest(String playerName, String cardName) {
        this.playerName = playerName;
        this.cardName = cardName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getCardName() {
        return cardName;
    }
}
