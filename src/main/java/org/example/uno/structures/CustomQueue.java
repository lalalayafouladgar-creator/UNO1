package org.example.uno.structures;

public class CustomQueue<T> {
    private Node<T> front;
    private Node<T> rear;
    private int size;

    public CustomQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public T peek(){
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    public T dequeue(){
        if (isEmpty()){
            return null;
        }
        T temp = front.data;
        front = front.next;
        size--;
        if (size == 0){
            rear = null;
        }
        return temp;
    }

    public void enqueue(T item){
        Node<T> newNode=new Node<>(item);
        if (front==null){
            front=newNode;
            rear=newNode;
        }
        else {
            rear.next=newNode;
            rear=newNode;
        }
        size++;
    }
}