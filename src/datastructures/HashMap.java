package datastructures;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {
    private static class Entry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V old = this.value;
            this.value = value;
            return old;
        }
    }

    private Entry<K, V>[] table;
    private int size;
    private static final int INITIAL_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                V old = entry.value;
                entry.value = value;
                return old;
            }
            entry = entry.next;
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
        return null;
    }

    @Override
    public V get(Object key) {
        int index = hash(key);
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                return entry.value;
            }
            entry = entry.next;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int index = hash(key);
        Entry<K, V> prev = null;
        Entry<K, V> entry = table[index];
        while (entry != null) {
            if (Objects.equals(entry.key, key)) {
                V value = entry.value;
                if (prev == null) table[index] = entry.next;
                else prev.next = entry.next;
                size--;
                return value;
            }
            prev = entry;
            entry = entry.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                set.add(entry.key);
                entry = entry.next;
            }
        }
        return set;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                set.add(entry);
                entry = entry.next;
            }
        }
        return set;
    }

    @Override
    public Collection<V> values() {
        LinkedList<V> list = new LinkedList<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                list.add(entry.value);
                entry = entry.next;
            }
        }
        return list;
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
        Arrays.fill(table, null);
        size = 0;
    }

    private int hash(Object key) {
        return key == null ? 0 : Math.abs(key.hashCode() % table.length);
    }

    // Map stubs
    @Override public void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    @Override public boolean containsValue(Object value) { throw new UnsupportedOperationException(); }
    @Override public boolean remove(Object key, Object value) { throw new UnsupportedOperationException(); }
    @Override public boolean replace(K key, V oldValue, V newValue) { throw new UnsupportedOperationException(); }
    @Override public V replace(K key, V value) { throw new UnsupportedOperationException(); }
    @Override public V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) { throw new UnsupportedOperationException(); }
}