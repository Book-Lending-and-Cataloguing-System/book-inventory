package datastructures;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import model.Transaction;

public class LendingTracker {
    private Queue<Transaction> transactions;

    public LendingTracker() {
        transactions = new LinkedList<>();
        loadTransactionsFromFile();
    }

    public void borrowBook(String bookISBN, String borrowerID) {
        // Create a new transaction for borrowing a book
        Transaction transaction = new Transaction(bookISBN, borrowerID, LocalDate.now(), "borrowed");
        transactions.add(transaction); // Add the transaction to the queue
    }

    public List<Transaction> getTransactions() {
        return new LinkedList<>(transactions);
    }
    
    public void addTransaction(Transaction transaction) {
        transactions.offer(transaction);
        saveTransactionToFile(transaction);
    }

    public boolean returnBook(String isbn, String borrowerId, String returnDateStr) {
        List<Transaction> tempList = new ArrayList<>();
        boolean updated = false;

        // Convert String to LocalDate (because Transaction expects LocalDate)
        LocalDate returnDate = LocalDate.parse(returnDateStr);

        while (!transactions.isEmpty()) {
            Transaction t = transactions.poll();

            // 🔥 Use the correct method name: getBookIsbn()
            if (!updated &&
                t.getBookIsbn().equals(isbn) &&
                t.getBorrowerId().equals(borrowerId) &&
                t.getStatus().equals("borrowed")) {

                t.setReturnDate(returnDate); // ✅ Pass LocalDate, not String
                t.setStatus("returned");
                updated = true;
            }

            tempList.add(t);
        }

        transactions.addAll(tempList);
        saveAllTransactions();
        return updated;
    }

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

    private void saveTransactionToFile(Transaction t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/transactions.txt", true))) {
            writer.write(t.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to save transaction: " + e.getMessage());
        }
    }

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
