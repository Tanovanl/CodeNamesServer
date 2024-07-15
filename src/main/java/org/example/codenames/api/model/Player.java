package org.example.codenames.api.model;

public class Player extends CodeNamesUser {
    private Team team;
    private Role role;

    public Player(String playerName, GameId gameId, Team team){
        super(gameId, playerName);
        this.team = team;
        this.role = Role.Operative;
    }

    public Team getTeam() {
        return team;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString(){
        return super.getPlayerName();
    }
}