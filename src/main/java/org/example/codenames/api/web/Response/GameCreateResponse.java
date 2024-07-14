package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Game;

public class GameCreateResponse {
    private Game game;
    private String playerName;

    public GameCreateResponse(Game game, String playerName) {
        this.game = game;
        this.playerName = playerName;
    }

    public Game getGame() {
        return game;
    }

    public String getPlayerName() {
        return playerName;
    }
}