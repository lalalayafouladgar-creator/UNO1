package org.example.uno.engine;

import org.example.uno.model.Player;
import org.example.uno.structures.DoublyCircularLinkedList;
import org.example.uno.structures.DoublyNode;

public class Game {
    private DoublyCircularLinkedList players;
    private Round currentRound;
    private String mode;
    private int targetLimit;
    private int roundsPlayed;
    private boolean finished;
    private Player winner;

    public Game(DoublyCircularLinkedList players, String mode, int targetLimit) {
        this.players = players;
        this.mode = mode;
        this.targetLimit = targetLimit;
        roundsPlayed = 0;
        finished = false;
        winner = null;
    }

    public boolean isFinished() {
        return finished;
    }

    public Player getWinner() {
        return winner;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void startNextRound() {
        if (finished) {
            return;
        }
        if (currentRound != null && !currentRound.isFinished()) {
            return;
        }
        currentRound = new Round(players);
        currentRound.startRound();
    }

    public void updateGame() {
        if (finished || currentRound == null) {
            return;
        }
        if (!currentRound.isFinished()) {
            return;
        }
        roundsPlayed++;
        if (mode.equals("score")) {
            DoublyNode current = players.getHead();
            for (int i = 0; i < players.size(); i++) {
                Player player = current.getData();
                if (player.getCurrentGameScore() >= targetLimit) {
                    winner = player;
                    finished = true;
                    return;
                }
                current = current.getNext();
            }
        }

        else if (mode.equals("rounds")) {
            if (roundsPlayed >= targetLimit) {
                DoublyNode current = players.getHead();
                winner = current.getData();
                for (int i = 0; i < players.size(); i++) {
                    Player player = current.getData();
                    if (player.getCurrentGameScore() > winner.getCurrentGameScore()) {
                        winner = player;
                    }
                    current = current.getNext();
                }
                finished = true;
                return;
            }
        }
    }
}