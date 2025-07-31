package datastructures;

import java.util.Iterator;

public class HashSet<T> implements Set<T> {
    private HashMap<T, Object> map;
    private static final Object DUMMY_VALUE = new Object();

    public HashSet() {
        map = new HashMap<>();
    }

    @Override
    public boolean add(T element) {
        return map.put(element, DUMMY_VALUE) == null;
    }

    @Override
    public Iterator<T> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
}