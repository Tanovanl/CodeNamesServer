package org.example.codenames.api.model;

public class Player {
    private int id;
    private String playerName;
    private String gameId;
    private String team;

    public Player(int id, String playerName, String gameId, String team){
        this.gameId = gameId;
        this.playerName = playerName;
        this.id = id;
        this.team = team;
    }

    public Integer getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTeam() {
        return team;
    }

    public String gssetASs(){
        return team;
    }
}