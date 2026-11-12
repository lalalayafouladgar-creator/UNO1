package org.example.uno.engine;

import org.example.uno.model.Card;
import org.example.uno.model.CardType;
import org.example.uno.structures.CustomStack;

public class DiscardPile {
    private CustomStack<Card> playedStack;

    public DiscardPile() {
        playedStack = new CustomStack<>();
    }

    public void addCard(Card card) {
        playedStack.push(card);
    }

    public Card getTopCard() {
        return playedStack.peek();
    }

    public Card removeTopCard() {
        return playedStack.pop();
    }

    public boolean isEmpty() {
        return playedStack.isEmpty();
    }

    public int size() {
        return playedStack.size();
    }

    public void firstCard(Deck deck) {
        Card card = deck.drawCard();
        while (card.getCardType() == CardType.WILD_DRAW_FOUR) {
            deck.returnAndShuffle(card);
            card = deck.drawCard();
        }
        playedStack.push(card);
    }
}
