package org.example.uno.engine;

import org.example.uno.model.*;

import org.example.uno.structures.CustomQueue;
import org.example.uno.structures.DoublyCircularLinkedList;
import org.example.uno.structures.DoublyNode;

import java.util.Random;

public class Deck {
    private CustomQueue<Card> drawQueue;

    public Deck() {
        drawQueue = new CustomQueue<>();
    }

    public boolean isEmpty() {
        return drawQueue.isEmpty();
    }

    public int size() {
        return drawQueue.size();
    }

    public void add(Card card){
        drawQueue.enqueue(card);
    }

    private void buildStandardDeck(Card[] cards) {
        int index = 0;
        Color[] colors = {
                Color.RED,
                Color.YELLOW,
                Color.GREEN,
                Color.BLUE
        };
        for (Color color : colors) {
            cards[index++] = new Card(color, CardType.NUMBER, 0);
            for (int value = 1; value <= 9; value++) {
                cards[index++] = new Card(color, CardType.NUMBER, value);
                cards[index++] = new Card(color, CardType.NUMBER, value);
            }
            for (int i = 0; i < 2; i++) {
                cards[index++] = new Card(color, CardType.SKIP, 20);
                cards[index++] = new Card(color, CardType.REVERSE, 20);
                cards[index++] = new Card(color, CardType.DRAW_TWO, 20);
            }
        }
        for (int i = 0; i < 4; i++) {
            cards[index++] = new Card(Color.NONE, CardType.WILD, 50);
            cards[index++] = new Card(Color.NONE, CardType.WILD_DRAW_FOUR, 50);
        }
    }

    private void shuffle(Card[] cards) {
        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }

    public void initializeDeck() {
        Card[] cards = new Card[108];
        buildStandardDeck(cards);
        shuffle(cards);
        for (int i = 0; i < cards.length; i++) {
            drawQueue.enqueue(cards[i]);
        }
    }

    public Card drawCard() {
        return drawQueue.dequeue();
    }

    public void rebuildDeck(DiscardPile discardPile) {
        if (discardPile.size() <= 1) {
            return;
        }

        Card topCard = discardPile.removeTopCard();
        int count = discardPile.size();
        Card[] cards = new Card[count];
        for (int i = 0; i < count; i++) {
            Card card = discardPile.removeTopCard();
            if (card.getCardType() == CardType.WILD || card.getCardType() == CardType.WILD_DRAW_FOUR) {
                card.setColor(Color.NONE);
            }
            cards[i] = card;
        }

        shuffle(cards);
        for (int i = 0; i < cards.length; i++) {
            drawQueue.enqueue(cards[i]);
        }
        discardPile.addCard(topCard);
    }

    public void giveCardToPlayer(DoublyCircularLinkedList players){
        DoublyNode current=players.getHead();
        for (int i=0 ; i< players.size() ; i++){
            for (int j=0 ; j<7 ; j++){
                current.getData().addCard(drawCard());
            }
            current=current.getNext();
        }
    }

    public void returnAndShuffle(Card card) {
        Card[] cards = new Card[drawQueue.size() + 1];
        cards[0] = card;
        for (int i = 1; i < cards.length; i++) {
            cards[i] = drawQueue.dequeue();
        }
        shuffle(cards);
        for (int i = 0; i < cards.length; i++) {
            drawQueue.enqueue(cards[i]);
        }
    }

}
