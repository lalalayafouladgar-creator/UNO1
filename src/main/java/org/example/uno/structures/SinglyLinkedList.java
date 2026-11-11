package org.example.uno.structures;

public class SinglyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;
    int size;

    public SinglyLinkedList(){
        head=null;
        tail=null;
        size=0;
    }

    public int getSize() {
        return size;
    }

    public Node<T> getTail() {
        return tail;
    }

    public Node<T> getHead() {
        return head;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public void addLast(T data) {
        Node<T> newNode = new Node<>(data);
        if (head==null){
            head=newNode;
            tail=newNode;
        }
        else {
            tail.next=newNode;
            tail=newNode;
        }
        size++;
    }
}
