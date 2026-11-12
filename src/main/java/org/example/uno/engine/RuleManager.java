
package org.example.uno.engine;

import org.example.uno.model.Card;
import org.example.uno.model.CardType;
import org.example.uno.model.Color;
import org.example.uno.structures.SinglyLinkedList;
import org.example.uno.structures.CustomStack;

public class RuleManager {

    public boolean isValidPlay(
            Card card,
            Card topCard,
            Color activeColor,
            SinglyLinkedList<Card> hand,
            CustomStack<Integer> penaltyStack) {

        CardType type = card.getCardType();
        if (!penaltyStack.isEmpty()) {
            return type == CardType.DRAW_TWO || type == CardType.WILD_DRAW_FOUR;
        }
        if (type == CardType.WILD_DRAW_FOUR) {
            for (int i = 0; i < hand.getSize(); i++) {
                Card handCard = hand.get(i);
                if (handCard.getColor() == activeColor) {
                    return false;
                }
            }
            return true;
        }
        if (type == CardType.WILD) {
            return true;
        }
        if (card.getColor() == activeColor) {
            return true;
        }
        if (type == CardType.NUMBER && topCard.getCardType() == CardType.NUMBER && card.getValue() == topCard.getValue()) {
            return true;
        }
        if (type != CardType.NUMBER && type == topCard.getCardType()) {
            return true;
        }

        return false;
    }
}
