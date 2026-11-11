package org.example.uno.structures;

import org.example.uno.model.Player;

public class DoublyCircularLinkedList {
    private DoublyNode head;
    private DoublyNode current;
    private int size;
    private boolean isClockwise;

    public DoublyCircularLinkedList() {
        head = null;
        current = null;
        size = 0;
        isClockwise = true;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public DoublyNode getHead() {
        return head;
    }

    public void addPlayer(Player p) {
        DoublyNode newNode = new DoublyNode(p);
        if (head == null) {
            head = newNode;
            newNode.next = newNode;
            newNode.prev = newNode;
            current = newNode;
        } else {
            DoublyNode last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
    }

    public Player getCurrentPlayer() {
        return current.data;
    }

    public Player peekNextPlayer() {
        if (isClockwise) {
            return current.next.data;
        } else {
            return current.prev.data;
        }
    }

    public void reverseDirection() {
        isClockwise = !isClockwise;
    }

    public void advanceTurn(int steps) {
        steps = steps % size;
        for (int i = 0; i < steps; i++) {
            if (isClockwise) {
                current = current.next;
            } else {
                current = current.prev;
            }
        }
    }
}