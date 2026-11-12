package org.example.uno.model;

public class Card {
    private Color color;
    private CardType cardType;
    private int value;

    public Card(Color color , CardType cardType , int value){
        this.color=color;
        this.cardType=cardType;
        this.value=value;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Color getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

