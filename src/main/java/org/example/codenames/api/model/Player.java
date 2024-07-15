package org.example.codenames.api.model;

public class Player extends CodeNamesUser {
    private Team team;
    private Role role;
    private boolean isLeader;

    public Player(String playerName, GameId gameId, Team team){
        super(gameId, playerName);
        this.team = team;
        this.role = Role.OPERATIVE;
        this.isLeader = false;
    }

    public Team getTeam() {
        return team;
    }

    public void setLeader(boolean isLeader){
        this.isLeader = isLeader;
    }

    public Role getRole() {
        return role;
    }

    public boolean getIsLeader(){
        return isLeader;
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