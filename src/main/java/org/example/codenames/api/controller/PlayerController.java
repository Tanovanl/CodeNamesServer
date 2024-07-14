package org.example.codenames.api.controller;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.model.Player;
import org.example.codenames.api.web.Response.AllGamesResponse;
import org.example.codenames.api.web.Response.GameCreateResponse;
import org.example.codenames.service.CodeNamesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {

    private CodeNamesServiceImpl codeNamesService;

    public PlayerController(CodeNamesServiceImpl playerService){
        this.codeNamesService = playerService;
    }

    @PostMapping("/game")
    public ResponseEntity<GameCreateResponse> createGame(@RequestParam String prefix, @RequestParam String gameName, @RequestParam String player){
        return codeNamesService.createGame(prefix, gameName, player);
    }

    @PostMapping("/game/{gameId}/player/{playerName}")
    public ResponseEntity<GameCreateResponse> addPlayer(@PathVariable String gameId, @PathVariable String playerName){
        return codeNamesService.addPlayer(gameId, playerName);
    }

    @GetMapping("/games")
    public ResponseEntity<AllGamesResponse> getGames(){
        return codeNamesService.getGames();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
