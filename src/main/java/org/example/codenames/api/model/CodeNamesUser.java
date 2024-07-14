package org.example.codenames.api.model;

public class CodeNamesUser {

    private GameId gameId;
    private String playerName;

    public CodeNamesUser(GameId gameId, String playerName){
        this.gameId = gameId;
        this.playerName = playerName;
    }

    public GameId getGameId(){
        try {
            String[] parts = gameId.toString().split("-");
            return new GameId(parts[0], parts[1]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gameId");
        }
    }

    public String getPlayerName() {
        return playerName;
    }


    @Override
    public String toString() {
        return getPlayerName();
    }
}
