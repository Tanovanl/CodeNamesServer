package org.example.codenames.service;

import org.example.codenames.api.model.*;
import org.example.codenames.api.web.Response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class CodeNamesServiceImpl {

    private final List<Game> games = new ArrayList<>();

    private void checkIfGameExists(GameId gameId) {
        for (Game game : games) {
            if (game.getGameId().toString().equals(gameId.toString())) {
                throw new IllegalArgumentException("Game already exists");
            }
        }
    }

    public ResponseEntity<GameCreateResponse> createGame(String prefix, String gameName, String playerName) {
        GameId gameId = new GameId(prefix, gameName);
        checkIfGameExists(gameId);
        Game game = new Game(gameId);
        Player player = new Player(playerName, gameId, Team.SPECTATOR);
        games.add(game);
        game.addPlayer(player);
        player.setLeader(true);

        GameCreateResponse gameResponse = new GameCreateResponse(game, player.getPlayerName());
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<AllGamesResponse> getGames() {
        return new ResponseEntity<>(new AllGamesResponse(games), HttpStatus.OK);
    }

    public ResponseEntity<GameCreateResponse> addPlayer(String gameId, String playerName) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        Player player = new Player(playerName, game.getGameIdObject(), Team.SPECTATOR);
        game.addPlayer(player);
        return new ResponseEntity<>(new GameCreateResponse(game, player.getPlayerName()), HttpStatus.CREATED);
    }


    public ResponseEntity<TeamJoinResponse> addPlayer(String gameId, String playerName, String team) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        if (game.getPlayerByName(playerName) == null) {
            throw new IllegalArgumentException("Player doesn't exist in the game");
        }
        if (!team.equals("RED") && !team.equals("BLUE")) {
            throw new IllegalArgumentException("Invalid team");
        }

        Player player = game.getPlayerByName(playerName);
        player.setTeam(Team.valueOf(team));
        return new ResponseEntity<>(new TeamJoinResponse(player.getTeam(), player.getRole(), playerName), HttpStatus.CREATED);
    }

    public ResponseEntity<RoleJoinResponse> addRolePlayer(String gameId, String playerName, String role) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        if (game.getPlayerByName(playerName) == null) {
            throw new IllegalArgumentException("Player doesn't exist in the game");
        }
        if (!role.equals("OPERATIVE") && !role.equals("SPYMASTER")) {
            throw new IllegalArgumentException("Invalid role");
        }

        Player player = game.getPlayerByName(playerName);
        player.setRole(Role.valueOf(role));
        return new ResponseEntity<>(new RoleJoinResponse(player.getTeam().toString(), player.getRole().toString(), playerName), HttpStatus.CREATED);
    }

    private Game getGameById(String gameId) {
        for (Game game : games) {
            if (game.getGameId().toString().equals(gameId)) {
                return game;
            }
        }
        return null;
    }

    public ResponseEntity<GameCreateResponse> removePlayer(String gameId, String playerName) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        Player player = game.getPlayerByName(playerName);
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }
        game.removePlayer(player);
        return new ResponseEntity<>(new GameCreateResponse(game, player.getPlayerName()), HttpStatus.OK);
    }

    public ResponseEntity<GetCardsResponse> getBoard(String gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return new ResponseEntity<>(new GetCardsResponse(game.getBoard().cards), HttpStatus.OK);
    }

    public ResponseEntity<GetGameDetailsResponse> getGame(String gameId) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new IllegalArgumentException("Game not found");
        }
        return new ResponseEntity<>(new GetGameDetailsResponse(game), HttpStatus.OK);
    }
}