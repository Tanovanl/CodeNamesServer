package org.example.codenames.api.model;

import java.util.Objects;

public class GameId {

    private final String prefix;
    private final String gameName;

    public GameId(String prefix, String gameName) {
        this.prefix = prefix;
        this.gameName = gameName;
    }

    public String getPrefix() {
        return prefix;
    }


    @Override
    public String toString() {
        return prefix + "-" + gameName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameId gameId = (GameId) o;
        return Objects.equals(toString(), gameId.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }

    public String getGameName() {
        return gameName;
    }
}