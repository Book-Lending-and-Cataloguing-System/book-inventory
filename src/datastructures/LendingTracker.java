package datastructures;

import model.Transaction;
import java.util.LinkedList;
import java.util.Queue;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LendingTracker {
    private Queue<Transaction> transactions;

    public LendingTracker() {
        transactions = new LinkedList<>();
        loadTransactionsFromFile();
    }

    public void addTransaction(Transaction transaction) {
        transactions.offer(transaction);
        saveTransactionToFile(transaction);
    }

    public boolean returnBook(String isbn, String borrowerId, String returnDateStr) {
        List<Transaction> tempList = new ArrayList<>();
        boolean updated = false;

        LocalDate returnDate = LocalDate.parse(returnDateStr);

        while (!transactions.isEmpty()) {
            Transaction t = transactions.poll();

            if (!updated &&
                t.getBookIsbn().equals(isbn) &&
                t.getBorrowerId().equals(borrowerId) &&
                t.getStatus().equals("borrowed")) {

                t.setReturnDate(returnDate);
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

    // ✅ This is the method Main.java needs
    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }
    public void loadTransactions(List<Transaction> transactionList) {
        transactions.clear();
        transactions.addAll(transactionList);
    }

}
