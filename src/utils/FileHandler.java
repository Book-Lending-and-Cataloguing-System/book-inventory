package utils;

import model.Book;
import model.Borrower;
import model.Transaction;
import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class FileHandler {
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String BORROWERS_FILE = "data/borrowers.txt";
    private static final String TRANSACTIONS_FILE = "data/transactions.txt";

    public void saveBooks(List<Book> books) throws IOException {
        // TODO: Implement saving books to books.txt
    }

    public List<Book> loadBooks() throws IOException {
        // TODO: Implement loading books from books.txt
        return null;
    }

    // TODO: Implement saveBorrowers() and loadBorrowers()
    // TODO: Implement saveTransactions() and loadTransactions()
    // TODO: Add error handling for file operations
}