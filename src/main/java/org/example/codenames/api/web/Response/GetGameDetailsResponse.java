package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Game;
import org.example.codenames.api.model.Player;
import org.example.codenames.api.model.Card;

import java.util.List;

public class GetGameDetailsResponse {
    private Game game;
    private List<Player> players;
    private List<Card> cards;

    public GetGameDetailsResponse(Game game){
        this.game = game;
        this.players = game.getPlayersObject();
        this.cards = game.getBoard().getCards();
    }

    public List<Player> getPlayers(){
        return players;
    }

    public List<Card> getCards(){
        return cards;
    }
}