package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Role;
import org.example.codenames.api.model.Team;

public class TeamJoinResponse {
    private Team team;
    private Role role;
    private String playerName;

    public TeamJoinResponse(Team team, Role role, String playerName){
        this.team = team;
        this.role = role;
        this.playerName = playerName;
    }

    public Team getTeam(){
        return team;
    }

    public Role getRole(){
        return role;
    }

    public String getPlayerName(){
        return playerName;
    }
}
