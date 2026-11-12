package org.example.uno.engine;

import org.example.uno.model.Card;
import org.example.uno.model.CardType;
import org.example.uno.model.Player;
import org.example.uno.structures.DoublyCircularLinkedList;
import org.example.uno.structures.DoublyNode;
import org.example.uno.structures.Node;

public class ScoreManager {

    public int calculateHandScore(Node<Card> node) {
        if (node == null) {
            return 0;
        }
        Card card = node.getData();
        int score;
        if (card.getCardType() == CardType.NUMBER) {
            score = card.getValue();
        }
        else if (card.getCardType() == CardType.WILD || card.getCardType() == CardType.WILD_DRAW_FOUR) {
            score = 50;
        }
        else {
            score = 20;
        }
        return score + calculateHandScore(node.getNext());
    }

    public int calculateRoundPoints(DoublyCircularLinkedList players, Player winner) {
        int totalPoints = 0;
        DoublyNode current = players.getHead();
        for (int i = 0; i < players.size(); i++) {
            Player player = current.getData();
            if (player != winner) {
                totalPoints += calculateHandScore(player.getHand().getHead());
            }
            current = current.getNext();
        }
        return totalPoints;
    }

    public void awardPoints(Player winner, int points) {
        winner.getRoundScores().addLast(points);
        winner.setCurrentGameScore(winner.getCurrentGameScore() + points);
        winner.getAccount().addScore(points);
    }

    public int finishRound(DoublyCircularLinkedList players, Player winner) {
        int points = calculateRoundPoints(players, winner);
        awardPoints(winner, points);
        return points;
    }
}