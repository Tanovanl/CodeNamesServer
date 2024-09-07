package org.example.codenames.api.web.Request;

public class GameLeaveRequest {
    private String player;

    public GameLeaveRequest() {
    }

    public GameLeaveRequest(String player) {
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
