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

    public void setIsStarted(boolean isStarted) {
        if (hasRequiredPlayersAndRoles()) {
            board.setUpCards();
            this.isStarted = isStarted;
        } else {
            throw new IllegalArgumentException("Game cannot be started. Ensure there are players in both teams and each team has an operative and a spymaster.");
        }
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

    private boolean hasRequiredPlayersAndRoles() {
        boolean hasRed = false;
        boolean hasBlue = false;
        boolean redHasOperative = false;
        boolean redHasSpymaster = false;
        boolean blueHasOperative = false;
        boolean blueHasSpymaster = false;

        for (Player player : players) {
            if (player.getTeam() == Team.RED) {
                hasRed = true;
                if (player.getRole() == Role.OPERATIVE) {
                    redHasOperative = true;
                } else if (player.getRole() == Role.SPYMASTER) {
                    redHasSpymaster = true;
                }
            } else if (player.getTeam() == Team.BLUE) {
                hasBlue = true;
                if (player.getRole() == Role.OPERATIVE) {
                    blueHasOperative = true;
                } else if (player.getRole() == Role.SPYMASTER) {
                    blueHasSpymaster = true;
                }
            }
        }

        return hasRed && hasBlue && redHasOperative && redHasSpymaster && blueHasOperative && blueHasSpymaster;
    }
}
