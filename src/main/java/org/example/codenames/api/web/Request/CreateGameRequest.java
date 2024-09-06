package org.example.codenames.api.web.Request;

public class CreateGameRequest {
    private String prefix;
    private String gameName;
    private String player;

    public CreateGameRequest(String prefix, String gameName, String player) {
        this.prefix = prefix;
        this.gameName = gameName;
        this.player = player;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getGameName() {
        return gameName;
    }

    public String getPlayer() {
        return player;
    }
}
