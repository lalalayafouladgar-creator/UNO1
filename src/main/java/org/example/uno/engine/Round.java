package org.example.uno.engine;

import org.example.uno.model.Card;
import org.example.uno.model.CardType;
import org.example.uno.model.Color;
import org.example.uno.model.Player;
import org.example.uno.structures.CustomStack;
import org.example.uno.structures.DoublyCircularLinkedList;
import org.example.uno.structures.DoublyNode;

public class Round {
    private Deck deck;
    private DiscardPile discardPile;
    private DoublyCircularLinkedList players;
    private Color activeColor;
    private CustomStack<Integer> penaltyStack;
    private TurnManager turnManager;
    private RuleManager ruleManager;
    private ScoreManager scoreManager;
    private boolean finished;
    private Player winner;

    public Round(DoublyCircularLinkedList players) {
        this.players = players;
        deck = new Deck();
        discardPile = new DiscardPile();
        turnManager = new TurnManager(players);
        ruleManager = new RuleManager();
        scoreManager = new ScoreManager();
        activeColor = Color.NONE;
        penaltyStack = new CustomStack<>();
        finished = false;
        winner = null;
    }

    public Player getCurrentPlayer() {
        return turnManager.getCurrentPlayer();
    }

    public Card getTopCard() {
        return discardPile.getTopCard();
    }

    public Color getActiveColor() {
        return activeColor;
    }

    public boolean hasPenalty() {
        return !penaltyStack.isEmpty();
    }

    public void startRound() {
        turnManager.resetTurnState();
        DoublyNode current = players.getHead();
        for (int i = 0; i < players.size(); i++) {
            Player player = current.getData();
            player.clearHand();
            current = current.getNext();
        }
        deck.initializeDeck();
        deck.giveCardToPlayer(players);
        discardPile.firstCard(deck);
        activeColor = discardPile.getTopCard().getColor();
    }

    public void applyFirstCardEffect(Color chosenColor) {
        Card firstCard = discardPile.getTopCard();
        CardType type = firstCard.getCardType();
        if (type == CardType.SKIP) {
            turnManager.advanceTurn(1);
        }
        else if (type == CardType.REVERSE) {
            if (players.size() == 2) {
                turnManager.advanceTurn(1);
            }
            else {
                turnManager.reverseDirection();
            }
        }
        else if (type == CardType.DRAW_TWO) {
            Player player = turnManager.getCurrentPlayer();
            for (int i = 0; i < 2; i++) {
                if (deck.isEmpty()) {
                    deck.rebuildDeck(discardPile);
                }
                player.addCard(deck.drawCard());
            }
            turnManager.advanceTurn(1);
        }
        else if (type == CardType.WILD) {
            activeColor = chosenColor;
        }
    }

    public boolean playCard(int cardIndex, Color chosenColor) {
        Player player = turnManager.getCurrentPlayer();
        Card card = player.getHand().get(cardIndex);
        if (!ruleManager.isValidPlay(card, discardPile.getTopCard(), activeColor, player.getHand(), penaltyStack)) {
            return false;
        }
        player.removeCard(cardIndex);
        discardPile.addCard(card);
        int steps = 1;
        if (card.getCardType() != CardType.WILD && card.getCardType() != CardType.WILD_DRAW_FOUR) {
            activeColor = card.getColor();
        }

        if (card.getCardType() == CardType.SKIP) {
            steps = 2;
        }
        else if (card.getCardType() == CardType.REVERSE) {
            if (players.size() == 2) {
                steps = 2;
            } else {
                turnManager.reverseDirection();
            }
        }
        else if (card.getCardType() == CardType.DRAW_TWO) {
            penaltyStack.push(2);
        }
        else if (card.getCardType() == CardType.WILD) {
            activeColor = chosenColor;
        }
        else if (card.getCardType() == CardType.WILD_DRAW_FOUR) {
            activeColor = chosenColor;
            penaltyStack.push(4);
        }

        if (player.getHand().isEmpty()) {
            winner = player;
            scoreManager.finishRound(players, winner);
            finished = true;
            return true;
        }
        turnManager.advanceTurn(steps);
        return true;
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getWinner() {
        return winner;
    }

    public Card drawCard() {
        if (finished || !penaltyStack.isEmpty()) {
            return null;
        }
        if (deck.isEmpty()) {
            deck.rebuildDeck(discardPile);
        }
        if (deck.isEmpty()) {
            return null;
        }
        Card card = deck.drawCard();
        turnManager.getCurrentPlayer().addCard(card);
        return card;
    }

    public void endTurn() {
        if (!finished) {
            turnManager.advanceTurn(1);
        }
    }

    private int calculatePenalty() {
        if (penaltyStack.isEmpty()) {
            return 0;
        }
        return penaltyStack.pop() + calculatePenalty();
    }

    public int drawPenalty() {
        if (penaltyStack.isEmpty() || finished) {
            return 0;
        }
        Player player = turnManager.getCurrentPlayer();
        int total = calculatePenalty();
        for (int i = 0; i < total; i++) {
            if (deck.isEmpty()) {
                deck.rebuildDeck(discardPile);
            }
            if (deck.isEmpty()) {
                break;
            }
            Card card = deck.drawCard();
            player.addCard(card);
        }
        turnManager.advanceTurn(1);
        return total;
    }
}
