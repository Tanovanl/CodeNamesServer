package org.example.codenames.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {

    private final GameId gameId;
    private final List<Player> players;
    private final Board board;
    private boolean isStarted;

    public Game(GameId gameId) {
        this.gameId = gameId;
        this.players = new ArrayList<>();
        board = new Board();
        isStarted = false;
    }

    public Map<String, Object> getGameIdAndPlayers() {
        return Map.of(
            "gameId", gameId.toString(),
            "players", getPlayers()
        );
    }

    public Board getBoard() {
        return board;
    }

    public String getGameId(){
        return gameId.toString();
    }

    @JsonIgnore
    public GameId getGameIdObject(){
        return gameId;
    }

    public List<Player> getPlayersObject(){
        return players;
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

    public Player getPlayerByName(String playerName) {
        for (Player player : players) {
            if (player.getPlayerName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}
