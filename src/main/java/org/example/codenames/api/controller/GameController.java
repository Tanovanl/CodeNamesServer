package org.example.codenames.api.controller;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.web.Request.CreateGameRequest;
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

    @PostMapping("/game")
    public ResponseEntity<GameCreateResponse> createGame(@RequestBody CreateGameRequest request){
        return codeNamesService.createGame(request.getPrefix(), request.getGameName(), request.getPlayer());
    }

    @PostMapping("/game/{gameId}/start")
    public ResponseEntity<GetGameDetailsResponse> startGame(@RequestBody StartGameRequest request, @PathVariable String gameId){
        return codeNamesService.startGame(gameId, request.getPlayerName());
    }

    @GetMapping("/games")
    public ResponseEntity<AllGamesResponse> getGames(){
        return codeNamesService.getGames();
    }

    @GetMapping("/game/{gameId}/board")
    public ResponseEntity<GetCardsResponse> getBoard(@PathVariable String gameId){
        return codeNamesService.getBoard(gameId);
    }

    @GetMapping("/game/{gameId}")
    public ResponseEntity<GetGameDetailsResponse> getGame(@PathVariable String gameId){
        return codeNamesService.getGame(gameId);
    }

    @PostMapping("/game/{gameId}")
    public ResponseEntity<GetGameDetailsResponse> guessCard(@PathVariable String gameId, @RequestParam String playerName, @RequestParam String card){
        return codeNamesService.guessCards(gameId, playerName, card);
    }
}