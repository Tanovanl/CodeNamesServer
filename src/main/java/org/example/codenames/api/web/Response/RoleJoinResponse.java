package org.example.codenames.api.web.Response;

public class RoleJoinResponse {
    private String playerName;
    private String role;
    private String team;

    public RoleJoinResponse(String playerName, String role, String team){
        this.playerName = playerName;
        this.role = role;
        this.team = team;
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getRole(){
        return role;
    }

    public String getTeam(){
        return team;
    }
}
