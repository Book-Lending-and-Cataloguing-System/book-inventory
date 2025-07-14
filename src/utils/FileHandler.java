package utils;

import model.Book;
import model.Borrower;
import model.Transaction;
import java.io.*;
import java.util.*;
import java.time.LocalDate;

public class FileHandler {
    // Constants for file paths
    private static final String DATA_DIR = "data/";
    private static final String BOOKS_FILE = DATA_DIR + "books.txt";
    private static final String BORROWERS_FILE = DATA_DIR + "borrowers.txt";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "transactions.txt";

    // Initialize data directory if it doesn't exist
    static {
        new File(DATA_DIR).mkdirs();
    }

    // ===== BOOK OPERATIONS =====

    /**
     * Saves books to file
     * @param books List of books to save
     * @throws IOException if file operation fails
     */
    public void saveBooks(List<Book> books) throws IOException {
        saveBooks(BOOKS_FILE, books);
    }

    /**
     * Saves books to specified file path
     * @param filePath Path to save books
     * @param books List of books to save
     * @throws IOException if file operation fails
     */
    public void saveBooks(String filePath, Object books) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Book book : object) {
                writer.println(book.toFileString());
            }
        }
    }

    /**
     * Loads books from default file
     * @return List of loaded books
     */
    public List<Book> loadBooks() {
        return loadBooks(BOOKS_FILE);
    }

    /**
     * Loads books from specified file path
     * @param filePath Path to load books from
     * @return List of loaded books
     */
    public List<Book> loadBooks(String filePath) {
        List<Book> books = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return books;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (!line.trim().isEmpty()) {
                        books.add(Book.fromFileString(line));
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid book entry: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    // ===== BORROWER OPERATIONS =====

    /**
     * Saves borrowers to file
     * @param borrowers List of borrowers to save
     * @throws IOException if file operation fails
     */
    public void saveBorrowers(List<Borrower> borrowers) throws IOException {
        saveBorrowers(BORROWERS_FILE, borrowers);
    }

    /**
     * Saves borrowers to specified file path
     * @param filePath Path to save borrowers
     * @param borrowers List of borrowers to save
     * @throws IOException if file operation fails
     */
    public void saveBorrowers(String filePath, Object borrowers) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Borrower borrower : object) {
                writer.println(borrower.toFileString());
            }
        }
    }

    /**
     * Loads borrowers from default file
     * @return List of loaded borrowers
     */
    public List<Borrower> loadBorrowers() {
        return loadBorrowers(BORROWERS_FILE);
    }

    /**
     * Loads borrowers from specified file path
     * @param filePath Path to load borrowers from
     * @return List of loaded borrowers
     */
    public List<Borrower> loadBorrowers(String filePath) {
        List<Borrower> borrowers = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return borrowers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (!line.trim().isEmpty()) {
                        borrowers.add(Borrower.fromFileString(line));
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid borrower entry: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading borrowers: " + e.getMessage());
        }
        return borrowers;
    }

    // ===== TRANSACTION OPERATIONS =====

    /**
     * Saves transactions to file
     * @param transactions List of transactions to save
     * @throws IOException if file operation fails
     */
    public void saveTransactions(List<Transaction> transactions) throws IOException {
        saveTransactions(TRANSACTIONS_FILE, transactions, null);
    }

    /**
     * Saves transactions to specified file path
     * @param filePath Path to save transactions
     * @param transactions List of transactions to save
     * @throws IOException if file operation fails
     */
    public void saveTransactions(String filePath, Object transactions) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Transaction txn : object) {
                writer.println(txn.toFileString());
            }
        }
    }

    /**
     * Loads transactions from default file
     * @return List of loaded transactions
     */
    public List<Transaction> loadTransactions() {
        return loadTransactions(TRANSACTIONS_FILE);
    }

    /**
     * Loads transactions from specified file path
     * @param filePath Path to load transactions from
     * @return List of loaded transactions
     */
    public List<Transaction> loadTransactions(String filePath) {
        List<Transaction> transactions = new ArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    if (!line.trim().isEmpty()) {
                        transactions.add(Transaction.fromFileString(line));
                    }
                } catch (Exception e) {
                    System.err.println("Skipping invalid transaction entry: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
        return transactions;
    }

    // ===== UTILITY METHODS =====

    /**
     * Creates backup of all data files
     * @throws IOException if backup fails
     */
    public void createBackup() throws IOException {
        LocalDate today = LocalDate.now();
        String backupDir = DATA_DIR + "backup_" + today + "/";
        new File(backupDir).mkdirs();
        
        copyFile(BOOKS_FILE, backupDir + "books.txt");
        copyFile(BORROWERS_FILE, backupDir + "borrowers.txt");
        copyFile(TRANSACTIONS_FILE, backupDir + "transactions.txt");
    }

    private void copyFile(String sourcePath, String destPath) throws IOException {
        try (InputStream in = new FileInputStream(sourcePath);
             OutputStream out = new FileOutputStream(destPath)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    public void saveTransactions(String string, Object listTransactions) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveTransactions'");
    }

    public void saveTransactions(String filePath, Object listTransactions) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveTransactions'");
    }

    public void saveTransactions(String filePath, Object listTransactions) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveTransactions'");
    }
}