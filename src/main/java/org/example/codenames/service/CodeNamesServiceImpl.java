package org.example.codenames.service;

import org.example.codenames.api.model.*;
import org.example.codenames.api.web.Response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeNamesServiceImpl {

    private final List<Game> games = new ArrayList<>();


    private void checkIfGameExists(GameId gameId) {
        for (Game game : games) {
            if (game.getGameId().equals(gameId.toString())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game already exists");
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found");
        }
        if (game.getPlayers().contains(playerName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player already exists in the game");
        }
        Player player = new Player(playerName, game.getGameIdObject(), Team.SPECTATOR);
        game.addPlayer(player);
        return new ResponseEntity<>(new GameCreateResponse(game, player.getPlayerName()), HttpStatus.CREATED);
    }


    public ResponseEntity<TeamJoinResponse> joinTeam(String gameId, String playerName, String team, String role) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found");
        }
        if (game.getPlayerByName(playerName) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Player doesn't exist in the game");
        }
        if (!team.equals("RED") && !team.equals("BLUE")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid team");
        }
        if (!role.equals("OPERATIVE") && !role.equals("SPYMASTER")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid role");
        }

        Player player = game.getPlayerByName(playerName);
        player.setRole(Role.valueOf(role));
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
        return new ResponseEntity<>(new RoleJoinResponse(playerName, player.getTeam().toString(), player.getRole().toString()), HttpStatus.CREATED);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player player = game.getPlayerByName(playerName);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Player not found");
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

    public ResponseEntity<GetGameDetailsResponse> startGame(String gameId, String playerName) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player player = game.getPlayerByName(playerName);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Player not found");
        }
        if (!player.getIsLeader()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the leader can start the game");
        }
        if (!game.hasRequiredPlayersAndRoles()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The game does not have the required players and roles");
        }
        game.setIsStarted(true);
        return new ResponseEntity<>(new GetGameDetailsResponse(game), HttpStatus.OK);
    }

    public ResponseEntity<GetGameDetailsResponse> guessCards(String gameId, String playerName, String card) {
        Game game = getGameById(gameId);
        Player player = game.getPlayerByName(playerName);

        // Check if a hint has been given
        if (game.getHintWord() == null || game.getHintNumber() == 0) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only guess after a hint is given");
        }

        exceptionsCards(game, player, card);



        game.getBoard().getCard(card).setIsRevealed(true);

        if (player.getTeam().equals(game.getBoard().getCard(card).getColor())) {
            game.setScore(player.getTeam(), 1);
        } else if (game.getBoard().getCard(card).getColor().equals(CardColor.BLACK)) {
            game.setScore(player.getTeam(), -100);
            game.setIsStarted(false);
        } else {
            game.setTurnToGuess(game.getTurnToGuess().equals(Team.RED) ? Team.BLUE : Team.RED);
            game.setHintWord(null);
            game.setHintNumber(0);
        }





        return new ResponseEntity<>(new GetGameDetailsResponse(game), HttpStatus.OK);
    }

    private void exceptionsCards(Game game, Player player, String card) {
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        if (!game.getIsStarted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Game has not started yet");
        }
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        if (player.getRole() != Role.OPERATIVE) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only operatives can guess cards");
        }
        if (player.getTeam() != game.getTurnToGuess()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "It is not your turn to guess!");
        }
        if (game.getBoard().getCard(card) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found");
        }
        if (game.getBoard().getCard(card).getIsRevealed()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card already revealed");
        }
    }

    public ResponseEntity<Player> getPlayer(String gameId, String playerName) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player player = game.getPlayerByName(playerName);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    public ResponseEntity<GiveHintResponse> giveHint(String gameId, String playerName, String hintWord, int hintNumber) {
        Game game = getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        Player player = game.getPlayerByName(playerName);
        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found");
        }
        if (!game.getIsStarted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Game has not started yet");
        }
        if (player.getRole() != Role.SPYMASTER) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only spymasters can give hints");
        }
        if (player.getTeam() != game.getTurnToGuess()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "It is not your team's turn to give a hint");
        }
        game.setHintWord(hintWord);
        game.setHintNumber(hintNumber);
        return new ResponseEntity<>(new GiveHintResponse(hintWord, hintNumber), HttpStatus.OK);
    }
}