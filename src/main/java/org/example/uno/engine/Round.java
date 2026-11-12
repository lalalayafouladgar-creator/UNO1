package org.example.uno.engine;

import org.example.uno.model.Card;
import org.example.uno.model.CardType;
import org.example.uno.model.Color;
import org.example.uno.model.Player;
import org.example.uno.structures.CustomStack;
import org.example.uno.structures.DoublyCircularLinkedList;

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

    public void startRound() {
        deck.initializeDeck();
        deck.giveCardToPlayer(players);
        discardPile.firstCard(deck);
        activeColor = discardPile.getTopCard().getColor();
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

    private int calculatePenalty(CustomStack<Integer> stack) {
        if (stack.isEmpty()) {
            return 0;
        }
        int value = stack.pop();
        int sum = value + calculatePenalty(stack);
        stack.push(value);
        return sum;
    }

    public int drawPenalty() {
        if (finished || penaltyStack.isEmpty()) {
            return 0;
        }
        int total = calculatePenalty(penaltyStack);
        Player player = turnManager.getCurrentPlayer();
        for (int i = 0; i < total; i++) {
            if (deck.isEmpty()) {
                deck.rebuildDeck(discardPile);
            }
            Card card = deck.drawCard();
            player.addCard(card);
        }
        while (!penaltyStack.isEmpty()) {
            penaltyStack.pop();
        }
        turnManager.advanceTurn(1);
        return total;
    }

}
