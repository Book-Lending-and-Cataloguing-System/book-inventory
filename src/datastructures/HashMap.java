package datastructures;

public class HashMap<K, V> {
    private static final int INITIAL_CAPACITY = 16;

    private Node<K, V>[] table;
    private int size;

    public HashMap() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void put(K key, V value) {
        int index = hash(key);
        Node<K, V> head = table[index];

        while (head != null) {
            if (head.key.equals(key)) {
                head.value = value;
                return;
            }
            head = head.next;
        }

        Node<K, V> newNode = new Node<>(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public V remove(K key) {
        int index = hash(key);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }

        return null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        table = new Node[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns all keys in the map as a typed array
     */
    public K[] keySetArray(K[] a) {
        if (a.length < size) {
            a = (K[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        for (Node<K, V> bucket : table) {
            while (bucket != null) {
                a[i++] = bucket.key;
                bucket = bucket.next;
            }
        }
        return a;
    }

    /**
     * Returns all values in the map as a typed array
     */
    public V[] valuesArray(V[] a) {
        if (a.length < size) {
            a = (V[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        int i = 0;
        for (Node<K, V> bucket : table) {
            while (bucket != null) {
                a[i++] = bucket.value;
                bucket = bucket.next;
            }
        }
        return a;
    }

    /**
     * Internal Node class
     */
    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
