package org.example.codenames.api.web.Response;

import org.example.codenames.api.model.Card;
import java.util.List;

public class GetCardsResponse {
    private List<Card> cards;

    public GetCardsResponse(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
