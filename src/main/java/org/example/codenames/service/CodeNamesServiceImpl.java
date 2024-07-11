package org.example.codenames.service;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.model.GameId;
import org.example.codenames.api.model.Player;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CodeNamesServiceImpl {

    private final List<Game> games = new ArrayList<>();

    public Game createGame(String prefix, String gameName, String player) {
        GameId gameId = new GameId(prefix, gameName);
        Game game = new Game(gameId);
        games.add(game);
        System.out.println(game);
        return game;
    }
}
