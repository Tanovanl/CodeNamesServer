package org.example.codenames.api.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Board {
    private final int MAX_POINTS = 8;
    private List<String> alreadyUsedWords;
    public List<Card> cards;

    public Board(){
        this.cards = null;
        alreadyUsedWords = new ArrayList<>();
    }

    public List<Card> getCards(){
        return cards;
    }

    public void setUpCards(){
        List<CardColor> colors = new ArrayList<>();
        for (int i = 0; i < MAX_POINTS; i++) {
            colors.add(CardColor.BLUE);
            colors.add(CardColor.RED);
        }
        for (int i = 0; i < MAX_POINTS; i++) {
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
        try (InputStream in = getClass().getResourceAsStream("/original.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line);
            }
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

    public Card getCard(String card) {
        for (Card c : cards) {
            if (c.getWord().equals(card)) {
                return c;
            }
        }
        return null;
    }
}
