package datastructures;

import java.lang.reflect.Array;

public class Queue<T> {
    private Node<T> front, rear;
    private int size;

    public Queue() {
        front = rear = null;
        size = 0;
    }

    public void offer(T value) {
        Node<T> newNode = new Node<>(value);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    public T poll() {
        if (isEmpty()) return null;
        T value = front.data;
        front = front.next;
        if (front == null) rear = null;
        size--;
        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        front = rear = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    /**
     * Unsafe version (kept for backward compatibility if needed)
     */
    @SuppressWarnings("unchecked")
    public T[] toArray() {
        T[] array = (T[]) new Object[size];
        int i = 0;
        Node<T> current = front;
        while (current != null) {
            array[i++] = current.data;
            current = current.next;
        }
        return array;
    }

    /**
     * ✅ Safe, typed version of toArray
     */
    @SuppressWarnings("unchecked")
    public T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        Node<T> current = front;
        while (current != null) {
            a[i++] = current.data;
            current = current.next;
        }

        if (a.length > size) {
            a[size] = null; // for compatibility with Collection.toArray contract
        }

        return a;
    }

    public void addAll(Queue<T> other) {
        for (T item : other.toArray()) {
            this.offer(item);
        }
    }

    private static class Node<T> {
        T data;
        Node<T> next;
        Node(T data) {
            this.data = data;
        }
    }
}
