package datastructures;

import model.Transaction;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * LendingTracker manages book borrowing and returns using a FIFO queue.
 * Transactions are logged and persisted in transactions.txt.
 */
public class LendingTracker {
    private Queue<Transaction> transactions;

    public LendingTracker() {
        transactions = new LinkedList<>();
        loadTransactionsFromFile();
    }

    /**
     * Adds a borrow transaction to the queue.
     * Queue is used to ensure borrowing is handled in order of request.
     */
    public void addTransaction(Transaction transaction) {
        transactions.offer(transaction);
        saveTransactionToFile(transaction);
    }

    /**
     * Marks a book as returned.
     * It updates the transaction with a return date and status.
     * This mimics dequeuing and reprocessing the record.
     */
    public boolean returnBook(String isbn, String borrowerId, String returnDate) {
        List<Transaction> tempList = new ArrayList<>();
        boolean updated = false;

        while (!transactions.isEmpty()) {
            Transaction t = transactions.poll();

            if (!updated && t.getIsbn().equals(isbn) && t.getBorrowerId().equals(borrowerId) && t.getStatus().equals("borrowed")) {
                t.setReturnDate(returnDate);
                t.setStatus("returned");
                updated = true;
            }

            tempList.add(t);
        }

        transactions.addAll(tempList);
        saveAllTransactions(); // rewrite entire file with updated data
        return updated;
    }

    /**
     * Displays all transactions in order.
     */
    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No lending transactions recorded yet.");
            return;
        }

        System.out.println("\nLending Transactions:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    /**
     * Loads all previous transactions from transactions.txt into the queue.
     */
    private void loadTransactionsFromFile() {
        File file = new File("data/transactions.txt");
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Transaction t = Transaction.fromFileString(line);
                transactions.offer(t);
            }
        } catch (IOException e) {
            System.out.println("Failed to load transactions: " + e.getMessage());
        }
    }

    /**
     * Saves a single transaction to the file (append mode).
     */
    private void saveTransactionToFile(Transaction t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/transactions.txt", true))) {
            writer.write(t.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to save transaction: " + e.getMessage());
        }
    }

    /**
     * Rewrites the entire transactions.txt file (used after return).
     */
    private void saveAllTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/transactions.txt", false))) {
            for (Transaction t : transactions) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to update transaction log: " + e.getMessage());
        }
    }

}
