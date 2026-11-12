
package org.example.uno.engine;

import org.example.uno.model.Player;
import org.example.uno.structures.DoublyCircularLinkedList;
import org.example.uno.structures.DoublyNode;

public class TurnManager {
    private DoublyCircularLinkedList players;
    private DoublyNode current;
    private boolean isClockwise;

    public TurnManager(DoublyCircularLinkedList players) {
        this.players = players;
        current = players.getHead();
        isClockwise = true;
    }

    public void addPlayer(Player player) {
        players.addPlayer(player);
        if (current == null) {
            current = players.getHead();
        }
    }

    public Player getCurrentPlayer() {
        return current.getData();
    }

    public Player peekNextPlayer() {
        if (isClockwise) {
            return current.getNext().getData();
        } else {
            return current.getPrev().getData();
        }
    }

    public void reverseDirection() {
        isClockwise = !isClockwise;
    }

    public void advanceTurn(int steps) {
        if (current == null || players.isEmpty()) {
            return;
        }
        steps = steps % players.size();
        for (int i = 0; i < steps; i++) {
            if (isClockwise) {
                current = current.getNext();
            } else {
                current = current.getPrev();
            }
        }
    }

    public void resetTurnState() {
        current = players.getHead();
        isClockwise = true;
    }
}
