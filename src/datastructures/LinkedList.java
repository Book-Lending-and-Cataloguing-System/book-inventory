package datastructures;

import java.util.*;

public class LinkedList<T> implements Queue<T>, List<T> {
    private class Node {
        T data;
        Node prev, next;
        Node(T data) {
            this.data = data;
        }
    }

    private Node head, tail;
    private int size;

    public LinkedList() {
        head = tail = null;
        size = 0;
    }

    // Queue methods
    @Override
    public boolean offer(T element) {
        add(element);
        return true;
    }

    @Override
    public T poll() {
        if (isEmpty()) return null;
        T data = head.data;
        head = head.next;
        if (head != null) head.prev = null;
        else tail = null;
        size--;
        return data;
    }

    @Override
    public T peek() {
        return isEmpty() ? null : head.data;
    }

    @Override
    public T element() {
        if (isEmpty()) throw new NoSuchElementException();
        return head.data;
    }

    @Override
    public T remove() {
        if (isEmpty()) throw new NoSuchElementException();
        return poll();
    }

    // List methods
    @Override
    public boolean add(T element) {
        Node node = new Node(element);
        if (isEmpty()) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) return false;
        for (T element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        if (c.isEmpty()) return false;
        Node prevNode = null;
        Node nextNode = head;
        for (int i = 0; i < index; i++) {
            prevNode = nextNode;
            nextNode = nextNode.next;
        }
        for (T element : c) {
            Node node = new Node(element);
            node.prev = prevNode;
            node.next = nextNode;
            if (prevNode != null) prevNode.next = node;
            else head = node;
            if (nextNode != null) nextNode.prev = node;
            else tail = node;
            prevNode = node;
            size++;
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private Node current = head;
            @Override
            public boolean hasNext() {
                return current != null;
            }
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // List and Queue stubs
    @Override public T set(int index, T element) { throw new UnsupportedOperationException(); }
    @Override public void add(int index, T element) { throw new UnsupportedOperationException(); }
    @Override public T remove(int index) { throw new UnsupportedOperationException(); }
    @Override public int indexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    @Override public ListIterator<T> listIterator() { throw new UnsupportedOperationException(); }
    @Override public ListIterator<T> listIterator(int index) { throw new UnsupportedOperationException(); }
    @Override public List<T> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
    @Override public boolean remove(Object o) { throw new UnsupportedOperationException(); }
    @Override public boolean contains(Object o) { throw new UnsupportedOperationException(); }
    @Override public Object[] toArray() { throw new UnsupportedOperationException(); }
    @Override public <E> E[] toArray(E[] a) { throw new UnsupportedOperationException(); }
    @Override public boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    @Override public void clear() { throw new UnsupportedOperationException(); }

    private void checkIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
    }
}