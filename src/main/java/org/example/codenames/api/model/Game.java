package org.example.codenames.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    private final GameId gameId;
    private final List<Player> players;
    private final Board board;
    private boolean isStarted;
    private Team currentTurnToGuess;
    private Map<Team, Integer> score;


    public Game(GameId gameId) {
        this.gameId = gameId;
        this.players = new ArrayList<>();
        board = new Board();
        currentTurnToGuess = null;
        isStarted = false;
        score = new HashMap<>();
        score.put(Team.RED, 0);
        score.put(Team.BLUE, 0);
    }

    public void setScore(Team team, int increment){
        score.put(team, score.get(team) + increment);
    }

    public Map<Team, Integer> getScore(){
        return score;
    }

    public Team getTurnToGuess(){
        return currentTurnToGuess;
    }

    public Map<String, Object> getGameIdAndPlayers() {
        return Map.of(
            "gameId", gameId.toString(),
            "players", getPlayers()
        );
    }

    private boolean canStartGame() {
        return players.size() >= 4 && hasRequiredPlayersAndRoles();
    }

    public void setIsStarted(boolean isStarted) {
        board.setUpCards();
        currentTurnToGuess = Team.BLUE;
        if (hasRequiredPlayersAndRoles()) {
            board.setUpCards();
            this.isStarted = isStarted;
            currentTurnToGuess = Team.BLUE;
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
        if  (isStarted) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game already started");
        }
        if (players.size() >= 4) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game is full");
        }
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

    public boolean hasRequiredPlayersAndRoles() {
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

    public boolean getIsStarted() {
        return isStarted;
    }

    public void setTurnToGuess(Team team) {
        currentTurnToGuess = team;
    }

    public Team getWinner() {
        if (score.get(Team.RED) >= 6 || score.get(Team.BLUE) < 0) {
            return Team.RED;
        } else if (score.get(Team.BLUE) >= 6 || score.get(Team.RED) < 0){
            return Team.BLUE;
        } else {
            return null;
        }
    }

    public Boolean getIsStartable() {
        return canStartGame();
    }
}
