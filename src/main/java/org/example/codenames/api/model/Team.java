package org.example.codenames.api.model;

public enum Team {
    BLUE,
    RED,
    SPECTATOR;

    public boolean equals(CardColor color) {
        if (color == null) {
            return false;
        }
        return this.name().equals(color.name());
    }
}
