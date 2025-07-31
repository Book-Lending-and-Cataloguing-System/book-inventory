package datastructures;

import java.util.*;

public class TreeMap<K extends Comparable<K>, V> implements Iterable<Map.Entry<K, V>> {

    private class Node {
        K key;
        V value;
        Node left, right;
        boolean red;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.red = true;
        }
    }

    private Node root;

    public TreeMap() {}

    public void put(K key, V value) {
        root = insert(root, key, value);
        root.red = false;
    }

    private Node insert(Node h, K key, V value) {
        if (h == null) return new Node(key, value);

        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = insert(h.left, key, value);
        else if (cmp > 0) h.right = insert(h.right, key, value);
        else h.value = value;

        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);

        return h;
    }

    public V get(K key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    public void putAll(TreeMap<K, V> other) {
        for (Map.Entry<K, V> entry : other) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public Collection<V> values() {
        List<V> result = new ArrayList<>();
        for (Map.Entry<K, V> entry : this) {
            result.add(entry.getValue());
        }
        return result;
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return 1 + size(x.left) + size(x.right);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public V remove(K key) {
        if (!containsKey(key)) return null;
        V[] removed = (V[]) new Object[1];
        root = remove(root, key, removed);
        if (root != null) root.red = false;
        return removed[0];
    }

    private Node remove(Node h, K key, V[] removed) {
        if (h == null) return null;

        int cmp = key.compareTo(h.key);
        if (cmp < 0) {
            h.left = remove(h.left, key, removed);
        } else if (cmp > 0) {
            h.right = remove(h.right, key, removed);
        } else {
            removed[0] = h.value;
            if (h.left == null) return h.right;
            if (h.right == null) return h.left;

            Node min = findMin(h.right);
            h.key = min.key;
            h.value = min.value;
            h.right = deleteMin(h.right);
        }

        return h;
    }

    private Node findMin(Node x) {
        while (x.left != null) x = x.left;
        return x;
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        return x;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        List<Map.Entry<K, V>> entries = new ArrayList<>();
        inorder(root, entries);
        return entries.iterator();
    }

    private void inorder(Node x, List<Map.Entry<K, V>> entries) {
        if (x == null) return;
        inorder(x.left, entries);
        entries.add(new AbstractMap.SimpleEntry<>(x.key, x.value));
        inorder(x.right, entries);
    }

    private boolean isRed(Node x) {
        return x != null && x.red;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.red = h.red;
        h.red = true;
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.red = h.red;
        h.red = true;
        return x;
    }

    private void flipColors(Node h) {
        h.red = true;
        if (h.left != null) h.left.red = false;
        if (h.right != null) h.right.red = false;
    }
}
