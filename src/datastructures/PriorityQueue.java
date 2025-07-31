package datastructures;

import java.util.Comparator;

public class PriorityQueue<T> {
    private T[] heap;
    private int size;
    private Comparator<T> comparator;

    public PriorityQueue(Comparator<T> comparator) {
        this.comparator = comparator;
        this.heap = (T[]) new Object[16];
        this.size = 0;
    }

    public void offer(T value) {
        if (size >= heap.length) resize();
        heap[size] = value;
        heapifyUp(size++);
    }

    public T poll() {
        if (isEmpty()) return null;
        T root = heap[0];
        heap[0] = heap[--size];
        heap[size] = null;
        heapifyDown(0);
        return root;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        heap = (T[]) new Object[16];
        size = 0;
    }

    private void resize() {
        T[] newHeap = (T[]) new Object[heap.length * 2];
        System.arraycopy(heap, 0, newHeap, 0, heap.length);
        heap = newHeap;
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (comparator.compare(heap[index], heap[parent]) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        while (index < size) {
            int left = 2 * index + 1, right = 2 * index + 2, smallest = index;

            if (left < size && comparator.compare(heap[left], heap[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && comparator.compare(heap[right], heap[smallest]) < 0) {
                smallest = right;
            }
            if (smallest == index) break;

            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        T tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}
