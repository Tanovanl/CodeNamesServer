package org.example.codenames.api.model;

public class Player extends CodeNamesUser {
    private Team team;

    public Player(String playerName, GameId gameId, Team team){
        super(gameId, playerName);
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }


    @Override
    public String toString(){
        return super.getPlayerName();
    }
}