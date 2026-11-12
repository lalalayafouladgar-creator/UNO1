
package org.example.uno.structures;

import org.example.uno.model.Player;

public class DoublyCircularLinkedList {

    private DoublyNode head;
    private int size;

    public DoublyCircularLinkedList() {
        head = null;
        size = 0;
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
        } else {
            DoublyNode last = head.prev;
            last.next = newNode;
            newNode.prev = last;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
    }
}
