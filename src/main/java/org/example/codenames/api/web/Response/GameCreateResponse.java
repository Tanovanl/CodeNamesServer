package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Game;

import java.util.Map;

public class GameCreateResponse {
    private Game game;
    private String playerName;

    public GameCreateResponse(Game game, String playerName) {
        this.game = game;
        this.playerName = playerName;
    }

    public Map<String, Object> getGame() {
        return game.getGameIdAndPlayers();
    }

    public String getPlayerName() {
        return playerName;
    }
}