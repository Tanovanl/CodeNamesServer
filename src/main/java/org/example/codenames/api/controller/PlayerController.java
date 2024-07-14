package org.example.codenames.api.controller;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.model.Player;
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

    @GetMapping("/user")
    public Player getPlayer(@RequestParam Integer id){
        return null;
       // return codeNamesService.getUser(id);
    }

    @PostMapping("/game")
    public ResponseEntity<GameCreateResponse> createGame(@RequestParam String prefix, @RequestParam String gameName, @RequestParam String player){
        return codeNamesService.createGame(prefix, gameName, player);
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames(){
        return codeNamesService.getGames();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
