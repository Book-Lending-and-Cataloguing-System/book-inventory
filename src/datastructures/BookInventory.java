package datastructures;

import model.Book;
import java.util.Map;
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
        booksByCategory.computeIfAbsent(book.getCategory(), k -> new TreeMap<>()).put(book.getIsbn(), book);
    }

    /**
     * Removes a book by ISBN.
     * Time complexity: O(log n) for finding category and ISBN.
     * @param isbn The ISBN of the book to remove.
     * @return true if the book was removed, false if not found.
     */
    public boolean removeBook(String isbn) {
        // TODO: Implement removal logic
        for (TreeMap<String, Book> books : booksByCategory.values()) {
            if (books.containsKey(isbn)) {
                books.remove(isbn);
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves books in a specific category, sorted by ISBN.
     * Time complexity: O(log n) for category lookup.
     * @param category The category to filter by.
     * @return TreeMap of ISBN to Book for the category.
     */
    public TreeMap<String, Book> getBooksByCategory(String category) {
        return booksByCategory.getOrDefault(category, new TreeMap<>());
    }

    /**
     * Lists all books in the inventory, grouped by category.
     * Time complexity: O(n) to traverse all books.
     */
    public void listBooks() {
        // TODO: Implement logic to print all books, grouped by category
    }

    /**
     * Filters books by a category prefix (e.g., "Fic" for Fiction).
     * Time complexity: O(n) to scan categories.
     * @param prefix The category prefix to match.
     * @return TreeMap of matching books.
     */
    public TreeMap<String, Book> filterByCategoryPrefix(String prefix) {
        // TODO: Implement prefix-based category filtering
        return new TreeMap<>();
    }

    // TODO: Implement listBooks() to display all books, grouped by category
    // TODO: Implement filterByCategoryPrefix() using TreeMap's subMap for range queries
    // TODO: Add method to get books within a category range (e.g., categories "A" to "F")
    // TODO: Document why TreeMap was chosen:
    //       - Pros: O(log n) lookup/insertion, sorted order for categories and ISBNs, efficient range queries
    //       - Cons: Slower than HashMap (O(1) lookup), higher memory overhead
    // TODO: Add Big O/Omega analysis for addBook (O(log n)), removeBook (O(log n)), listBooks (O(n))
}