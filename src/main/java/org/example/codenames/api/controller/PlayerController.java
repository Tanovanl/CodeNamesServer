package org.example.codenames.api.controller;

import org.example.codenames.api.model.Player;
import org.example.codenames.api.web.Request.AddPlayerRequest;
import org.example.codenames.api.web.Request.GameLeaveRequest;
import org.example.codenames.api.web.Request.RoleJoinRequest;
import org.example.codenames.api.web.Request.TeamJoinRequest;
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

    @PostMapping("/{gameId}/join")
    public ResponseEntity<GameCreateResponse> addPlayer(@PathVariable String gameId, @RequestBody AddPlayerRequest request){
        return codeNamesService.addPlayer(gameId, request.getPlayer());
    }

    @GetMapping("/{gameId}/player/{playerName}")
    public ResponseEntity<Player> getPlayerInfo(@PathVariable String gameId, @PathVariable String playerName) {
        return codeNamesService.getPlayer(gameId, playerName);
    }

    @PostMapping("/{gameId}/player/team")
    public ResponseEntity<TeamJoinResponse> addPlayer(@PathVariable String gameId, @RequestBody TeamJoinRequest request){
        return codeNamesService.addPlayer(gameId, request.getPlayer(), request.getTeam());
    }

    @PostMapping("/{gameId}/player/role")
    public ResponseEntity<RoleJoinResponse> addRolePlayer(@PathVariable String gameId, @RequestBody RoleJoinRequest request){
        return codeNamesService.addRolePlayer(gameId, request.getPlayer(), request.getRole());
    }

    @DeleteMapping("/{gameId}/player/leave")
    public ResponseEntity<GameCreateResponse> removePlayer(@PathVariable String gameId, @RequestBody GameLeaveRequest request){
        return codeNamesService.removePlayer(gameId, request.getPlayer());
    }


    
}
