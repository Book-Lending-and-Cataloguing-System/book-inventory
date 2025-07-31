package datastructures;

import java.util.*;

public class PriorityQueue<T> {
    private T[] heap;
    private int size;
    private Comparator<? super T> comparator;

    @SuppressWarnings("unchecked")
    public PriorityQueue(Comparator<? super T> comparator) {
        this.comparator = comparator;
        heap = (T[]) new Object[16];
        size = 0;
    }

    public boolean offer(T element) {
        if (size == heap.length) {
            heap = Arrays.copyOf(heap, heap.length * 2);
        }
        heap[size] = element;
        siftUp(size);
        size++;
        return true;
    }

    public T poll() {
        if (isEmpty()) return null;
        T result = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        if (size > 0) siftDown(0);
        return result;
    }

    public T peek() {
        return isEmpty() ? null : heap[0];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(heap, 0, size, null);
        size = 0;
    }

    private void siftUp(int index) {
        T element = heap[index];
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare(element, heap[parent]) >= 0) break;
            heap[index] = heap[parent];
            index = parent;
        }
        heap[index] = element;
    }

    private void siftDown(int index) {
        T element = heap[index];
        int half = size / 2;
        while (index < half) {
            int child = 2 * index + 1;
            int right = child + 1;
            if (right < size && comparator.compare(heap[child], heap[right]) > 0) {
                child = right;
            }
            if (comparator.compare(element, heap[child]) <= 0) break;
            heap[index] = heap[child];
            index = child;
        }
        heap[index] = element;
    }
}