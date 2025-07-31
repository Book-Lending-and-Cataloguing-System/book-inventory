package datastructures;

import java.util.Collection;
import java.util.Iterator;

public interface Set<T> extends Collection<T> {
    boolean add(T element);
    Iterator<T> iterator();
    int size();
    boolean isEmpty();
    // Minimal stubs for Collection
    default boolean contains(Object o) { throw new UnsupportedOperationException(); }
    default Object[] toArray() { throw new UnsupportedOperationException(); }
    default <E> E[] toArray(E[] a) { throw new UnsupportedOperationException(); }
    default boolean remove(Object o) { throw new UnsupportedOperationException(); }
    default boolean containsAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    default boolean addAll(Collection<? extends T> c) { throw new UnsupportedOperationException(); }
    default boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    default boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }
    default void clear() { throw new UnsupportedOperationException(); }
}