package org.example.uno.structures;
import java.util.Comparator;

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

    public T get(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current=head;
        for (int i=0 ; i<index ; i++){
            current=current.next;
        }
        return current.data;
    }


    public T removeAt(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> removed;
        if (index == 0) {
            removed = head;
            head = head.next;
            if (size == 1) {
                tail = null;
            }
        } else {
            Node<T> previous = head;
            for (int i = 0; i < index - 1; i++) {
                previous = previous.next;
            }
            removed = previous.next;
            previous.next = removed.next;
            if (removed == tail) {
                tail = previous;
            }
        }
        size--;
        return removed.data;
    }

    private Node<T> getMiddle(Node<T> start) {
        Node<T> slow = start;
        Node<T> fast = start;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private Node<T> merge(Node<T> left, Node<T> right, Comparator<T> cmp) {
        Node<T> resultHead;
        Node<T> current;
        if (cmp.compare(left.data, right.data) <= 0) {
            resultHead = left;
            left = left.next;
        } else {
            resultHead = right;
            right = right.next;
        }
        current = resultHead;
        while (left != null && right != null) {
            if (cmp.compare(left.data, right.data) <= 0) {
                current.next = left;
                left = left.next;
            } else {
                current.next = right;
                right = right.next;
            }
            current = current.next;
        }
        if (left != null) {
            current.next = left;
        } else {
            current.next = right;
        }
        return resultHead;
    }

    private Node<T> mergeSort(Node<T> start, Comparator<T> cmp) {
        if (start == null || start.next == null) {
            return start;
        }
        Node<T> middle = getMiddle(start);
        Node<T> secondHalf = middle.next;
        middle.next = null;
        Node<T> left = mergeSort(start, cmp);
        Node<T> right = mergeSort(secondHalf, cmp);
        return merge(left, right, cmp);
    }

    public void sort(Comparator<T> cmp) {
        if (head == null || head.next == null) {
            return;
        }
        head = mergeSort(head, cmp);
    }

    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
