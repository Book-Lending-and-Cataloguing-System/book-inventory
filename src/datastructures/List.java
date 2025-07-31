package datastructures;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public interface List<T> extends Collection<T> {
    boolean add(T element);
    T get(int index);
    Iterator<T> iterator();
    boolean addAll(Collection<? extends T> c);
    boolean addAll(int index, Collection<? extends T> c);
    int size();
    boolean isEmpty();
    void clear();
    // Minimal stubs for Collection and List
    default T set(int index, T element) { throw new UnsupportedOperationException(); }
    default void add(int index, T element) { throw new UnsupportedOperationException(); }
    default T remove(int index) { throw new UnsupportedOperationException(); }
    default int indexOf(Object o) { throw new UnsupportedOperationException(); }
    default int lastIndexOf(Object o) { throw new UnsupportedOperationException(); }
    default ListIterator<T> listIterator() { throw new UnsupportedOperationException(); }
    default ListIterator<T> listIterator(int index) { throw new UnsupportedOperationException(); }
    default List<T> subList(int fromIndex, int toIndex) { throw new UnsupportedOperationException(); }
    default boolean remove(Object o) { throw new UnsupportedOperationException(); }
    default boolean contains(Object o) { throw new UnsupportedOperationException(); }
    default Object[] toArray() { throw new UnsupportedOperationException(); }
    default <E> E[] toArray(E[] a) { throw new UnsupportedOperationException(); }
    default boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    default boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    default boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
}