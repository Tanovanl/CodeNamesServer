package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllGamesResponse {

    private List<Game> games;

    public AllGamesResponse(List<Game> games){
        this.games = games;
    }

    public List<Map<String, Object>> getGames(){
        List<Map<String, Object>> gameList = new ArrayList<>();
        for (Game game : games) {
            gameList.add(game.getGameIdAndPlayers());
        }
        return gameList;
    }
}
