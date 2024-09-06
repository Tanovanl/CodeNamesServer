package org.example.codenames.api.web.Request;

import org.example.codenames.api.model.GameId;

public class StartGameRequest {
    private String playerName;

    public StartGameRequest() {
    }

    public StartGameRequest(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}