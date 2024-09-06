package org.example.codenames.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.example.codenames.api.web.Request.CreateGameRequest;
import org.example.codenames.api.web.Request.GuessCardRequest;
import org.example.codenames.api.web.Request.StartGameRequest;
import org.example.codenames.api.web.Response.AllGamesResponse;
import org.example.codenames.api.web.Response.GameCreateResponse;
import org.example.codenames.api.web.Response.GetCardsResponse;
import org.example.codenames.api.web.Response.GetGameDetailsResponse;
import org.example.codenames.service.CodeNamesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class GameController {

    private CodeNamesServiceImpl codeNamesService;

    public GameController(CodeNamesServiceImpl codeNamesService) {
        this.codeNamesService = codeNamesService;
    }

    @Operation(summary = "Create a new game", description = "This endpoint creates a new game with the provided details.")
    @PostMapping("/game")
    public ResponseEntity<GameCreateResponse> createGame(@RequestBody CreateGameRequest request){
        return codeNamesService.createGame(request.getPrefix(), request.getGameName(), request.getPlayer());
    }

    @Operation(summary = "Start a game", description = "This endpoint starts a game with the provided game ID and player name.")
    @PostMapping("/game/{gameId}/start")
    public ResponseEntity<GetGameDetailsResponse> startGame(@RequestBody StartGameRequest request, @PathVariable String gameId){
        return codeNamesService.startGame(gameId, request.getPlayerName());
    }

    @Operation(summary = "Get all games", description = "This endpoint retrieves all the games.")
    @GetMapping("/games")
    public ResponseEntity<AllGamesResponse> getGames(){
        return codeNamesService.getGames();
    }

    @Operation(summary = "Get game board", description = "This endpoint retrieves the game board for the provided game ID.")
    @GetMapping("/game/{gameId}/board")
    public ResponseEntity<GetCardsResponse> getBoard(@PathVariable String gameId){
        return codeNamesService.getBoard(gameId);
    }

    @Operation(summary = "Get game details", description = "This endpoint retrieves the details of the game with the provided game ID.")
    @GetMapping("/game/{gameId}")
    public ResponseEntity<GetGameDetailsResponse> getGame(@PathVariable String gameId){
        return codeNamesService.getGame(gameId);
    }

    @Operation(summary = "Guess a card", description = "This endpoint allows a player to guess a card in the game with the provided game ID.")
    @PostMapping("/game/{gameId}")
    public ResponseEntity<GetGameDetailsResponse> guessCard(@PathVariable String gameId, @RequestBody GuessCardRequest request){
        return codeNamesService.guessCards(gameId, request.getPlayerName(), request.getCardName());
    }
}