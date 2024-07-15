package org.example.codenames.api.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Board {
    private final int BOARD_SIZE = 25;
    private List<String> alreadyUsedWords;
    public List<Card> cards;

    public Board(){
        this.cards = null;
        alreadyUsedWords = new ArrayList<>();
        setUpCards();
    }

    public List<Card> getCards(){
        return cards;
    }

    public void setUpCards(){
        List<CardColor> colors = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            colors.add(CardColor.BLUE);
            colors.add(CardColor.RED);
        }
        for (int i = 0; i < 8; i++) {
            colors.add(CardColor.GRAY);
        }
        colors.add(CardColor.BLACK);

        Collections.shuffle(colors);

        cards = new ArrayList<>();
        for (CardColor color : colors) {
            cards.add(new Card(color, getRandomCardWord()));
        }
    }

    private String getRandomCardWord(){
        List<String> words = new ArrayList<>();
        try {
            words = Files.readAllLines(Paths.get("src/main/java/org/example/codenames/api/original.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String randomWord;
        do {
            int randomIndex = new Random().nextInt(words.size());
            randomWord = words.get(randomIndex);
        } while (alreadyUsedWords.contains(randomWord));

        alreadyUsedWords.add(randomWord);
        return randomWord;
    }
}
