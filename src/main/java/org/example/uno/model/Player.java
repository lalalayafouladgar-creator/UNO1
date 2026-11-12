package org.example.uno.model;

import org.example.uno.structures.SinglyLinkedList;

public abstract class Player {
    private Account account ;
    private String name;
    private SinglyLinkedList<Card> hand;
    private SinglyLinkedList<Integer> roundScores ;
    private int currentGameScore;

    public Player(Account account, String name) {
        this.account = account;
        this.name = name;
        hand = new SinglyLinkedList<>();
        roundScores = new SinglyLinkedList<>();
        currentGameScore = 0;
    }

    public String getName() {
        return name;
    }

    public Account getAccount() {
        return account;
    }

    public int getCurrentGameScore() {
        return currentGameScore;
    }

    public SinglyLinkedList<Card> getHand() {
        return hand;
    }

    public SinglyLinkedList<Integer> getRoundScores() {
        return roundScores;
    }

    public void setCurrentGameScore(int score) {
        currentGameScore = score;
    }

    public void addCard(Card card) {
        hand.addLast(card);
    }

    public Card removeCard(int index) {
        return hand.removeAt(index);
    }

    public void clearHand() {
        hand.clear();
    }
}
