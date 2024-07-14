package org.example.codenames.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final GameId gameId;
    private final List<Player> players;

    public Game(GameId gameId) {
        this.gameId = gameId;
        this.players = new ArrayList<>();
    }

    public String getGameId(){
        return gameId.toString();
    }

    @JsonIgnore
    public GameId getGameIdObject(){
        return gameId;
    }

    public List<String> getPlayers() {
        List<String> playerNames = new ArrayList<>();
        for (Player player : players) {
            playerNames.add(player.getPlayerName());
        }
        return playerNames;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    @Override
    public String toString(){
        return gameId.toString();
    }
}
