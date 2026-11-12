package org.example.uno.structures;
import org.example.uno.model.Player;

public class DoublyNode {
    Player data;
    DoublyNode next;
    DoublyNode prev;

    public DoublyNode(Player data) {
        this.data = data;
        this.next = null;
        this.prev=null;
    }

    public Player getData() {
        return data;
    }

    public DoublyNode getNext() {
        return next;
    }
}