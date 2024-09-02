package org.example.codenames.api.controller;

import org.example.codenames.api.model.Player;
import org.example.codenames.api.web.Response.*;
import org.example.codenames.service.CodeNamesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/game/{gameId}/player/{playerName}")
    public ResponseEntity<Player> getPlayerInfo(@PathVariable String gameId, @PathVariable String playerName) {
        return codeNamesService.getPlayer(gameId, playerName);
    }

    @PostMapping("/game/{gameId}/start")
    public ResponseEntity<GetGameDetailsResponse> startGame(@PathVariable String gameId, @RequestParam String playerName){
        return codeNamesService.startGame(gameId, playerName);
    }

    @PostMapping("/game/{gameId}/player/{playerName}/team/{team}")
    public ResponseEntity<TeamJoinResponse> addPlayer(@PathVariable String gameId, @PathVariable String playerName, @PathVariable String team){
        return codeNamesService.addPlayer(gameId, playerName, team);
    }

    @PostMapping("/game/{gameId}/player/{playerName}/role/{role}")
    public ResponseEntity<RoleJoinResponse> addRolePlayer(@PathVariable String gameId, @PathVariable String playerName, @PathVariable String role){
        return codeNamesService.addRolePlayer(gameId, playerName, role);
    }

    @DeleteMapping("/game/{gameId}/player/{playerName}")
    public ResponseEntity<GameCreateResponse> removePlayer(@PathVariable String gameId, @PathVariable String playerName){
        return codeNamesService.removePlayer(gameId, playerName);
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
