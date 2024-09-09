package org.example.codenames.api.web.Request;

public class TeamJoinRequest {
    private String team;
    private String role;
    private String player;


    public TeamJoinRequest(String team, String role, String player) {
        this.team = team;
        this.player = player;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
