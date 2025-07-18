package datastructures;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class BookInventory {
    // TreeMap for sorted order by category, with nested TreeMap for sorted ISBNs
    private TreeMap<String, TreeMap<String, Book>> booksByCategory;

    public BookInventory() {
        booksByCategory = new TreeMap<>();
    }

    /**
     * Adds a book to the inventory, organized by category.
     * Time complexity: O(log n) due to TreeMap insertion.
     * @param book The book to add.
     */
    public void addBook(Book book) {
        booksByCategory
            .computeIfAbsent(book.getCategory(), k -> new TreeMap<>())
            .put(book.getIsbn(), book);
    }

    /**
     * Removes a book by ISBN across all categories.
     * Time complexity: O(log n) per category check.
     * @param isbn The ISBN of the book to remove.
     * @return true if the book was removed, false if not found.
     */
    public boolean removeBook(String isbn) {
        for (Map.Entry<String, TreeMap<String, Book>> entry : booksByCategory.entrySet()) {
            TreeMap<String, Book> books = entry.getValue();
            if (books.containsKey(isbn)) {
                books.remove(isbn);
                // Clean up empty categories
                if (books.isEmpty()) {
                    booksByCategory.remove(entry.getKey());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves books in a specific category, sorted by ISBN.
     * @param category The category to filter by.
     * @return TreeMap of ISBN to Book for the category, or empty if none found.
     */
    public TreeMap<String, Book> getBooksByCategory(String category) {
        return booksByCategory.getOrDefault(category, new TreeMap<>());
    }

    /**
     * Lists all books in the inventory, grouped by category.
     * Each category and its books are shown in sorted order.
     */
    public void listBooks() {
        System.out.println("\n--- Listing the books in the library ---");

        if (booksByCategory.isEmpty()) {
            System.out.println("The library inventory is currently empty.");
            return;
        }

        for (Map.Entry<String, TreeMap<String, Book>> entry : booksByCategory.entrySet()) {
            String category = entry.getKey();
            TreeMap<String, Book> books = entry.getValue();

            System.out.println("\nCategory: " + category);
            for (Book book : books.values()) {
                System.out.println(book);
            }
        }
    }


    /**
     * Filters books by a category prefix (e.g., "Fic" for Fiction).
     * Efficiently uses subMap to get range of matching categories.
     * @param prefix The category prefix to match.
     * @return TreeMap of matching categories and their books.
     */
    public TreeMap<String, Book> filterByCategoryPrefix(String prefix) {
        TreeMap<String, Book> result = new TreeMap<>();

        if (prefix == null || prefix.isEmpty()) {
            return result;
        }

        String upperBound = prefix + Character.MAX_VALUE;
        NavigableMap<String, TreeMap<String, Book>> subCategories = booksByCategory.subMap(prefix, true, upperBound, true);

        for (Map.Entry<String, TreeMap<String, Book>> entry : subCategories.entrySet()) {
            result.putAll(entry.getValue());
        }

        return result;
    }

    /**
     * Gets all books in categories within a given alphabetical range.
     * Useful for browsing or partial reports.
     * @param fromCategory Start of the category range (inclusive).
     * @param toCategory End of the category range (inclusive).
     * @return Combined TreeMap of books in those categories.
     */
    public TreeMap<String, Book> getBooksInCategoryRange(String fromCategory, String toCategory) {
        TreeMap<String, Book> result = new TreeMap<>();
        NavigableMap<String, TreeMap<String, Book>> range = booksByCategory.subMap(fromCategory, true, toCategory, true);

        for (TreeMap<String, Book> books : range.values()) {
            result.putAll(books);
        }

        return result;
    }

    /**
     * Returns the total number of books in inventory.
     */
    public int totalBookCount() {
        return booksByCategory.values().stream().mapToInt(Map::size).sum();
    }

    public void loadBooks(List<Book> books) {
        for (Book book : books) {
            addBook(book);
        }
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        for (TreeMap<String, Book> bookMap : booksByCategory.values()) {
            allBooks.addAll(bookMap.values());
        }
        return allBooks;
    }

    public Book findBook(String isbn) {
        for (TreeMap<String, Book> categoryBooks : booksByCategory.values()) {
            if (categoryBooks.containsKey(isbn)) {
                return categoryBooks.get(isbn);
            }
        }
        return null; // Book not found
    }



}
