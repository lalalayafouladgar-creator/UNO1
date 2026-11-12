package org.example.uno.structures;

import java.util.Arrays;

public class DynamicArray<T> {
    private T[] arr;
    private int size;
    private int capacity;

    public DynamicArray() {
        capacity = 10;
        size = 0;
        arr = (T[]) new Object[capacity];
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return arr[index];
    }

    public int size() {
        return size;
    }

    public void set(int index, T item) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        arr[index] = item;
    }

    private void resize() {
        capacity = capacity * 2;
        T[] newArr = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newArr[i] = arr[i];
        }
        arr = newArr;
    }

    public void add(T item) {
        if (size == capacity) {
            resize();
        }
        arr[size] = item;
        size++;
    }

    public void swap(int i, int j) {
        if (i < 0 || i >= size || j < 0 || j >= size) {
            throw new IndexOutOfBoundsException();
        }
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}