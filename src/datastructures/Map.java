package datastructures;

import java.util.Collection;

public interface Map<K, V> {
    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
    }

    V put(K key, V value);
    V get(Object key);
    V remove(Object key);
    boolean containsKey(Object key);
    Set<K> keySet();
    Set<Entry<K, V>> entrySet();
    Collection<V> values();
    int size();
    boolean isEmpty();
    void clear();
    // Minimal stubs
    default void putAll(Map<? extends K, ? extends V> m) { throw new UnsupportedOperationException(); }
    default boolean containsValue(Object value) { throw new UnsupportedOperationException(); }
    default boolean remove(Object key, Object value) { throw new UnsupportedOperationException(); }
    default boolean replace(K key, V oldValue, V newValue) { throw new UnsupportedOperationException(); }
    default V replace(K key, V value) { throw new UnsupportedOperationException(); }
    default V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) { throw new UnsupportedOperationException(); }
}