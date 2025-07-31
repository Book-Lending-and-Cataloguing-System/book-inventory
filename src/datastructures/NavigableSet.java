package datastructures;

import java.util.Comparator;

public interface NavigableSet<T> extends Set<T>, SortedSet<T> {
    T lower(T e);
    T floor(T e);
    T ceiling(T e);
    T higher(T e);
    T pollFirst();
    T pollLast();
    NavigableSet<T> descendingSet();
    NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive);
    NavigableSet<T> headSet(T toElement, boolean inclusive);
    NavigableSet<T> tailSet(T fromElement, boolean inclusive);
}