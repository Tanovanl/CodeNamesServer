package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Game;

import java.util.List;

public class AllGamesResponse {

    private List<Game> games;

    public AllGamesResponse(List<Game> games){
        this.games = games;
    }

    public List<Game> getGames(){
        return games;
    }
}
