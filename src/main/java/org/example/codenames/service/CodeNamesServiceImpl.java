package org.example.codenames.service;

import org.example.codenames.api.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CodeNamesServiceImpl {

    private List<Player> playerList;

    public CodeNamesServiceImpl(){
        playerList = new ArrayList<>();

        Player player = new Player(1, "PlayerName", "GameId", "Team");
        Player player2 = new Player(2, "PlayerName", "GameId", "Team");
        Player player3 = new Player(3, "PlayerName", "GameId", "Team");
        Player player4 = new Player(4, "PlayerName", "GameId", "Team");

        playerList.addAll(Arrays.asList(player, player2, player3, player4));
    }

    public Player getUser(Integer id) {
        for (Player player : playerList){
            if (id == player.getId()){
                return player;
            }
        }
        throw new IllegalArgumentException("Player not found!");
    }

    public void createGame(String prefix, String gameName, String player) {

    }
}
