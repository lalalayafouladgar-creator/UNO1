package org.example.uno.controller;

import org.example.uno.engine.Game;
import org.example.uno.engine.Round;
import org.example.uno.model.Card;
import org.example.uno.model.Color;
import org.example.uno.model.Player;

public class GameController {
    private Game game;

    public GameController(Game game) {
        this.game = game;
    }

    public void startGame() {
        game.startNextRound();
    }

    public void startNextRound() {
        game.startNextRound();
    }

    public void applyFirstCardEffect(Color chosenColor) {
        game.getCurrentRound().applyFirstCardEffect(chosenColor);
    }

    public boolean playCard(int cardIndex, Color chosenColor) {
        return game.getCurrentRound().playCard(cardIndex, chosenColor);
    }

    public Card drawCard() {
        return game.getCurrentRound().drawCard();
    }

    public int drawPenalty() {
        return game.getCurrentRound().drawPenalty();
    }

    public void endTurn() {
        game.getCurrentRound().endTurn();
    }

    public void updateGame() {
        game.updateGame();
        if (!game.isFinished() && game.getCurrentRound().isFinished()) {
            game.startNextRound();
        }
    }

    public Round getCurrentRound() {
        return game.getCurrentRound();
    }

    public Player getCurrentPlayer() {
        return game.getCurrentRound().getCurrentPlayer();
    }

    public boolean isGameFinished() {
        return game.isFinished();
    }

    public Player getWinner() {
        return game.getWinner();
    }
}