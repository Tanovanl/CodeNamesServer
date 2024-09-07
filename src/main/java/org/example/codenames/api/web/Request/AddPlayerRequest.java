package org.example.codenames.api.web.Request;

public class AddPlayerRequest {
    private String player;

    public AddPlayerRequest() {
    }

    public AddPlayerRequest(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}