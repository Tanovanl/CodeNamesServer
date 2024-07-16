package org.example.codenames.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Card {
    public CardColor color;
    public String word;
    public boolean revealed;

    public Card(CardColor color, String word){
        this.color = color;
        this.word = word;
        this.revealed = false;
    }

    public CardColor getColor(){
        return color;
    }

    public String getWord(){
        return word;
    }

    @Override
    public String toString(){
        return word + " " + color.toString() + " " + revealed;
    }

    @JsonIgnore
    public boolean getIsRevealed() {
        return revealed;
    }

    public void setIsRevealed(boolean revealed) {
        this.revealed = revealed;
    }
}
