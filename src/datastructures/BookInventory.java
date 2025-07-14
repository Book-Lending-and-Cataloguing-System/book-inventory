package datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import model.Book;

public class BookInventory {
    private final TreeMap<String, List<Book>> booksByCategory;

    public BookInventory() {
        booksByCategory = new TreeMap<>(); // Automatically sorts by category name
    }
public List<String> getAllCategories() {
    return new ArrayList<>(booksByCategory.keySet());
}

    // Method to add a book to a specific category
    public void addBook(String category, Book book) {
        booksByCategory.computeIfAbsent(category, k -> new ArrayList<>()).add(book);
    }

    // Method to load a list of books into the inventory
    public void loadBooks(List<Book> books) {
        for (Book book : books) {
            addBook(book.getCategory(), book);
        }
    }

    // Method to get all books across all categories
    public List<Book> getAllBooks() {
        List<Book> allBooks = new ArrayList<>();
        booksByCategory.values().forEach(allBooks::addAll);
        return allBooks;
    }

    // Method to list all books (could be an alias for getAllBooks)
    public void listBooks() {
        for (List<Book> books : booksByCategory.values()) {
            for (Book book : books) {
                System.out.println(book.getTitle() + " by " + book.getAuthor());
            }
        }
    }

    // Method to remove a book by ISBN (or any unique identifier)
    public boolean removeBook(String isbn) {
        for (List<Book> books : booksByCategory.values()) {
            for (Book book : books) {
                if (book.getIsbn().equals(isbn)) {
                    books.remove(book);
                    return true; // Book removed
                }
            }
        }
        return false; // Book not found
    }

    // Method to get books by specific category
    public List<Book> getBooksByCategory(String category) {
        return booksByCategory.getOrDefault(category, new ArrayList<>());
    }

    // Method to filter books by category prefix
    public List<Book> filterByCategoryPrefix(String prefix) {
        List<Book> filteredBooks = new ArrayList<>();
        for (String category : booksByCategory.keySet()) {
            if (category.startsWith(prefix)) {
                filteredBooks.addAll(booksByCategory.get(category));
            }
        }
        return filteredBooks;
    }
}
