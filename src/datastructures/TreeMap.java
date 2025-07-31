package datastructures;

import java.util.*;

public class TreeMap<K extends Comparable<K>, V> implements NavigableMap<K, V> {
    private class Node {
        K key;
        V value;
        Node left, right;
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node root;
    private int size;

    public TreeMap() {
        root = null;
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        Node node = new Node(key, value);
        if (root == null) {
            root = node;
            size++;
            return null;
        }
        V oldValue = put(root, key, value);
        if (oldValue == null) size++;
        return oldValue;
    }

    private V put(Node node, K key, V value) {
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            V old = node.value;
            node.value = value;
            return old;
        }
        if (cmp < 0) {
            if (node.left == null) {
                node.left = new Node(key, value);
                return null;
            }
            return put(node.left, key, value);
        } else {
            if (node.right == null) {
                node.right = new Node(key, value);
                return null;
            }
            return put(node.right, key, value);
        }
    }

    @Override
    public V get(Object key) {
        Node node = get(root, (K) key);
        return node != null ? node.value : null;
    }

    private Node get(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        return cmp < 0 ? get(node.left, key) : get(node.right, key);
    }

    @Override
    public V remove(Object key) {
        V value = get(key);
        if (value == null) return null;
        root = remove(root, (K) key);
        size--;
        return value;
    }

    private Node remove(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node min = findMin(node.right);
            node.key = min.key;
            node.value = min.value;
            node.right = remove(node.right, min.key);
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public Collection<V> values() {
        LinkedList<V> list = new LinkedList<>();
        inOrder(root, list);
        return list;
    }

    private void inOrder(Node node, LinkedList<V> list) {
        if (node == null) return;
        inOrder(node.left, list);
        list.add(node.value);
        inOrder(node.right, list);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> set = new HashSet<>();
        entrySet(root, set);
        return set;
    }

    private void entrySet(Node node, Set<Map.Entry<K, V>> set) {
        if (node == null) return;
        entrySet(node.left, set);
        set.add(new EntryWrapper(node.key, node.value));
        entrySet(node.right, set);
    }

    private class EntryWrapper implements Map.Entry<K, V> {
        private K key;
        private V value;

        EntryWrapper(K key, V value) {
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
            throw new UnsupportedOperationException("Cannot modify TreeMap entries");
        }
    }

    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        TreeMap<K, V> sub = new TreeMap<>();
        subMap(root, sub, fromKey, fromInclusive, toKey, toInclusive);
        return sub;
    }

    private void subMap(Node node, TreeMap<K, V> sub, K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        if (node == null) return;
        int cmpFrom = node.key.compareTo(fromKey);
        int cmpTo = node.key.compareTo(toKey);
        if (cmpFrom > (fromInclusive ? 0 : 1)) {
            subMap(node.left, sub, fromKey, fromInclusive, toKey, toInclusive);
        }
        if ((fromInclusive ? cmpFrom >= 0 : cmpFrom > 0) && (toInclusive ? cmpTo <= 0 : cmpTo < 0)) {
            sub.put(node.key, node.value);
        }
        if (cmpTo < (toInclusive ? 0 : -1)) {
            subMap(node.right, sub, fromKey, fromInclusive, toKey, toInclusive);
        }
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
    public Comparator<? super K> comparator() {
        return null; // Natural ordering
    }

    @Override
    public K firstKey() {
        if (root == null) throw new NoSuchElementException();
        Node node = findMin(root);
        return node.key;
    }

    @Override
    public K lastKey() {
        if (root == null) throw new NoSuchElementException();
        Node node = findMax(root);
        return node.key;
    }

    private Node findMax(Node node) {
        while (node.right != null) node = node.right;
        return node;
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        if (root == null) return null;
        Node node = findMin(root);
        return new EntryWrapper(node.key, node.value);
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        if (root == null) return null;
        Node node = findMax(root);
        return new EntryWrapper(node.key, node.value);
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K key) {
        Node node = lowerNode(root, key);
        return node == null ? null : new EntryWrapper(node.key, node.value);
    }

    private Node lowerNode(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp <= 0) return lowerNode(node.left, key);
        Node right = lowerNode(node.right, key);
        return right != null ? right : node;
    }

    @Override
    public K lowerKey(K key) {
        Node node = lowerNode(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public Map.Entry<K, V> floorEntry(K key) {
        Node node = floorNode(root, key);
        return node == null ? null : new EntryWrapper(node.key, node.value);
    }

    private Node floorNode(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp < 0) return floorNode(node.left, key);
        Node right = floorNode(node.right, key);
        return right != null ? right : node;
    }

    @Override
    public K floorKey(K key) {
        Node node = floorNode(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K key) {
        Node node = ceilingNode(root, key);
        return node == null ? null : new EntryWrapper(node.key, node.value);
    }

    private Node ceilingNode(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp > 0) return ceilingNode(node.right, key);
        Node left = ceilingNode(node.left, key);
        return left != null ? left : node;
    }

    @Override
    public K ceilingKey(K key) {
        Node node = ceilingNode(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public Map.Entry<K, V> higherEntry(K key) {
        Node node = higherNode(root, key);
        return node == null ? null : new EntryWrapper(node.key, node.value);
    }

    private Node higherNode(Node node, K key) {
        if (node == null) return null;
        int cmp = key.compareTo(node.key);
        if (cmp >= 0) return higherNode(node.right, key);
        Node left = higherNode(node.left, key);
        return left != null ? left : node;
    }

    @Override
    public K higherKey(K key) {
        Node node = higherNode(root, key);
        return node == null ? null : node.key;
    }

    @Override
    public Map.Entry<K, V> pollFirstEntry() {
        if (root == null) return null;
        Node min = findMin(root);
        Map.Entry<K, V> entry = new EntryWrapper(min.key, min.value);
        root = remove(root, min.key);
        return entry;
    }

    @Override
    public Map.Entry<K, V> pollLastEntry() {
        if (root == null) return null;
        Node max = findMax(root);
        Map.Entry<K, V> entry = new EntryWrapper(max.key, max.value);
        root = remove(root, max.key);
        return entry;
    }

    @Override
    public NavigableMap<K, V> descendingMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<K> navigableKeySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableSet<K> descendingKeySet() {
        throw new UnsupportedOperationException();
    }

    // Removed duplicate subMap(K fromKey, K toKey) method to resolve compilation error.

    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        keySet(root, set);
        return set;
    }

    private void keySet(Node node, Set<K> set) {
        if (node == null) return;
        keySet(node.left, set);
        set.add(node.key);
        keySet(node.right, set);
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
}