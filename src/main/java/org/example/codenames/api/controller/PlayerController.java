package org.example.codenames.api.controller;

import org.example.codenames.api.model.Player;
import org.example.codenames.api.web.Response.*;
import org.example.codenames.service.CodeNamesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class PlayerController {

    private CodeNamesServiceImpl codeNamesService;

    public PlayerController(CodeNamesServiceImpl playerService){
        this.codeNamesService = playerService;
    }

    @PostMapping("/{gameId}/player/{playerName}")
    public ResponseEntity<GameCreateResponse> addPlayer(@PathVariable String gameId, @PathVariable String playerName){
        return codeNamesService.addPlayer(gameId, playerName);
    }

    @GetMapping("/{gameId}/player/{playerName}")
    public ResponseEntity<Player> getPlayerInfo(@PathVariable String gameId, @PathVariable String playerName) {
        return codeNamesService.getPlayer(gameId, playerName);
    }

    @PostMapping("/{gameId}/player/{playerName}/team/{team}")
    public ResponseEntity<TeamJoinResponse> addPlayer(@PathVariable String gameId, @PathVariable String playerName, @PathVariable String team){
        return codeNamesService.addPlayer(gameId, playerName, team);
    }

    @PostMapping("/{gameId}/player/{playerName}/role/{role}")
    public ResponseEntity<RoleJoinResponse> addRolePlayer(@PathVariable String gameId, @PathVariable String playerName, @PathVariable String role){
        return codeNamesService.addRolePlayer(gameId, playerName, role);
    }

    @DeleteMapping("/{gameId}/player/{playerName}")
    public ResponseEntity<GameCreateResponse> removePlayer(@PathVariable String gameId, @PathVariable String playerName){
        return codeNamesService.removePlayer(gameId, playerName);
    }


    
}
