package org.example.codenames.service;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.model.GameId;
import org.example.codenames.api.model.Player;
import org.example.codenames.api.model.Team;
import org.example.codenames.api.web.Response.AllGamesResponse;
import org.example.codenames.api.web.Response.GameCreateResponse;
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

        GameCreateResponse gameResponse = new GameCreateResponse(game, player.getPlayerName());
        return new ResponseEntity<>(gameResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<AllGamesResponse> getGames() {
        return new ResponseEntity<>(new AllGamesResponse(games), HttpStatus.OK);
    }
}