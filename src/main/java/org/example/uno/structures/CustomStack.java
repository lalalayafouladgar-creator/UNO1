package org.example.uno.structures;

public class CustomStack<T> {

    private Node<T> top;
    private int size;

    public CustomStack() {
        top = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }
        T temp = top.data;
        top = top.next;
        size--;
        return temp;
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }
}