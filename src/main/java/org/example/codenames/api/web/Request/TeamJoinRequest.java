package org.example.codenames.api.web.Request;

public class TeamJoinRequest {
    private String team;
    private String player;

    public TeamJoinRequest() {
    }

    public TeamJoinRequest(String team, String player) {
        this.team = team;
        this.player = player;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}
