package utils;

import java.io.*;
import java.util.*;
import model.Book;
import model.Borrower;
import model.Transaction;

public class FileHandler {
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String BORROWERS_FILE = "data/borrowers.txt";
    private static final String TRANSACTIONS_FILE = "data/transactions.txt";

    // ===== BOOKS =====

    public void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books) {
                writer.println(book.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    books.add(Book.fromFileString(line));
                } catch (Exception e) {
                    System.out.println("Invalid book line skipped: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    // ===== BORROWERS =====

    public void saveBorrowers(List<Borrower> borrowers) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BORROWERS_FILE))) {
            for (Borrower borrower : borrowers) {
                writer.println(borrower.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving borrowers: " + e.getMessage());
        }
    }

    public List<Borrower> loadBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        File file = new File(BORROWERS_FILE);
        if (!file.exists()) return borrowers;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    borrowers.add(Borrower.fromFileString(line));
                } catch (Exception e) {
                    System.out.println("Invalid borrower line skipped: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading borrowers: " + e.getMessage());
        }
        return borrowers;
    }

    // ===== TRANSACTIONS =====

    public void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction txn : transactions) {
                writer.println(txn.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    public List<Transaction> loadTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return transactions;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    transactions.add(Transaction.fromFileString(line));
                } catch (Exception e) {
                    System.out.println("Invalid transaction line skipped: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }
    FileHandler fileHandler = new FileHandler();

List<Book> books = fileHandler.loadBooks();
List<Borrower> borrowers = fileHandler.loadBorrowers();
List<Transaction> transactions = fileHandler.loadTransactions();

}
