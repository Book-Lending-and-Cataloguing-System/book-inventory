package utils;

import model.Book;

import java.util.ArrayList;
import java.util.List;

public class SortUtil {

    /**
     * Merge Sort by Book Title (Alphabetical)
     * Time: O(n log n), Space: O(n)
     */
    public static void mergeSortByTitle(List<Book> books) {
        if (books.size() <= 1) return;

        int mid = books.size() / 2;
        List<Book> left = new ArrayList<>(books.subList(0, mid));
        List<Book> right = new ArrayList<>(books.subList(mid, books.size()));

        mergeSortByTitle(left);
        mergeSortByTitle(right);

        mergeByTitle(books, left, right);
    }

    private static void mergeByTitle(List<Book> books, List<Book> left, List<Book> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            if (left.get(i).getTitle().compareToIgnoreCase(right.get(j).getTitle()) <= 0) {
                books.set(k++, left.get(i++));
            } else {
                books.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) books.set(k++, left.get(i++));
        while (j < right.size()) books.set(k++, right.get(j++));
    }

    /**
     * Selection Sort by Year (Oldest to Newest)
     * Time: O(n²), Space: O(1)
     */
    public static void selectionSortByYear(List<Book> books) {
        int n = books.size();
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (books.get(j).getYear() < books.get(minIndex).getYear()) {
                    minIndex = j;
                }
            }

            // Swap
            Book temp = books.get(i);
            books.set(i, books.get(minIndex));
            books.set(minIndex, temp);
        }
    }

    /**
     * Insertion Sort by Author Name (Alphabetical)
     * Time: O(n²), Space: O(1)
     * Performs better on nearly sorted data
     */
    public static void insertionSortByAuthor(List<Book> books) {
        for (int i = 1; i < books.size(); i++) {
            Book key = books.get(i);
            int j = i - 1;

            while (j >= 0 && books.get(j).getAuthor().compareToIgnoreCase(key.getAuthor()) > 0) {
                books.set(j + 1, books.get(j));
                j--;
            }

            books.set(j + 1, key);
        }
    }

    /**
     * Merge Sort by Category, then Title
     * Used for organizing the inventory shelf-style
     */
    public static void mergeSortByCategoryThenTitle(List<Book> books) {
        if (books.size() <= 1) return;

        int mid = books.size() / 2;
        List<Book> left = new ArrayList<>(books.subList(0, mid));
        List<Book> right = new ArrayList<>(books.subList(mid, books.size()));

        mergeSortByCategoryThenTitle(left);
        mergeSortByCategoryThenTitle(right);

        mergeByCategoryThenTitle(books, left, right);
    }

    private static void mergeByCategoryThenTitle(List<Book> books, List<Book> left, List<Book> right) {
        int i = 0, j = 0, k = 0;

        while (i < left.size() && j < right.size()) {
            Book b1 = left.get(i);
            Book b2 = right.get(j);

            int categoryCompare = b1.getCategory().compareToIgnoreCase(b2.getCategory());
            int titleCompare = b1.getTitle().compareToIgnoreCase(b2.getTitle());

            if (categoryCompare < 0 || (categoryCompare == 0 && titleCompare <= 0)) {
                books.set(k++, b1);
                i++;
            } else {
                books.set(k++, b2);
                j++;
            }
        }

        while (i < left.size()) books.set(k++, left.get(i++));
        while (j < right.size()) books.set(k++, right.get(j++));
    }

    /**
     * Sort books by ISBN using simple Bubble Sort
     * Time: O(n²), suitable for small datasets
     */
    public static void bubbleSortByIsbn(List<Book> books) {
        int n = books.size();

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (books.get(j).getIsbn().compareToIgnoreCase(books.get(j + 1).getIsbn()) > 0) {
                    // Swap
                    Book temp = books.get(j);
                    books.set(j, books.get(j + 1));
                    books.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Justification of Sorting Algorithms
     *
     * Merge Sort:
     * - Time Complexity: O(n log n)
     * - Space: O(n)
     * - Stable and fast, ideal for large datasets
     * - Best for sorting books by title, or category + title
     *
     * Selection Sort:
     * - Time: O(n²), Space: O(1)
     * - Simple to implement
     * - Best for sorting books by year when dataset is small
     *
     * Insertion Sort:
     * - Time: O(n²), but O(n) if nearly sorted
     * - Best for sorting authors or during file reordering
     *
     * Bubble Sort:
     * - Inefficient, O(n²)
     * - Used here only for ISBN as fallback
     *
     * Recommendations:
     * - Use merge sort when working with large inventory lists
     * - Use insertion sort or selection sort when dealing with short user-specific results
     */
}
