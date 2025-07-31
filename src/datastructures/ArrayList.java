package datastructures;

import java.util.*;

public class ArrayList<T> implements List<T> {
    private T[] array;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        array = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public boolean add(T element) {
        if (size == array.length) {
            array = Arrays.copyOf(array, array.length * 2);
        }
        array[size++] = element;
        return true;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < size;
            }
            @Override
            public T next() {
                if (!hasNext()) throw new NoSuchElementException();
                return array[index++];
            }
        };
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T element : c) {
            add(element);
        }
        return !c.isEmpty();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        int cSize = c.size();
        if (cSize == 0) return false;
        if (size + cSize > array.length) {
            array = Arrays.copyOf(array, Math.max(array.length * 2, size + cSize));
        }
        // Shift elements to the right
        System.arraycopy(array, index, array, index + cSize, size - index);
        // Add new elements
        int i = index;
        for (T element : c) {
            array[i++] = element;
        }
        size += cSize;
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(array, 0, size, null);
        size = 0;
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
    }

    // List stubs
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
}