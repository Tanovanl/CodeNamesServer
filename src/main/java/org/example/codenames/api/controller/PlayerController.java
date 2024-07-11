package org.example.codenames.api.controller;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.model.Player;
import org.example.codenames.service.CodeNamesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private CodeNamesServiceImpl codeNamesService;

    public PlayerController(CodeNamesServiceImpl playerService){
        this.codeNamesService = playerService;
    }

    @GetMapping("/user")
    public Player getPlayer(@RequestParam Integer id){
        return codeNamesService.getUser(id);
    }

    @GetMapping("/game")
    public Game createGame(@RequestParam String prefix, @RequestParam String gameName, @RequestParam String player){
        codeNamesService.createGame(prefix, gameName, player);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
