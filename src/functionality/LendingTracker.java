package functionality;

import model.Transaction;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import datastructures.Queue;

public class LendingTracker {
    private Queue<Transaction> transactions;

    public LendingTracker() {
        transactions = new Queue<>();
        loadTransactionsFromFile();
    }

    public void addTransaction(Transaction transaction) {
        transactions.offer(transaction);
        saveTransactionToFile(transaction);
    }

    public boolean returnBook(String isbn, String borrowerId, String returnDateStr) {
        Queue<Transaction> tempQueue = new Queue<>();
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

            tempQueue.offer(t);
        }

        transactions.addAll(tempQueue);
        saveAllTransactions();
        return updated;
    }

    public void listTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No lending transactions recorded yet.");
            return;
        }

        System.out.println("\nLending Transactions:");
        for (Transaction t : transactions.toArray(new Transaction[0])) {
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
            for (Transaction t : transactions.toArray(new Transaction[0])) {
                writer.write(t.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to update transaction log: " + e.getMessage());
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        for (Transaction t : transactions.toArray(new Transaction[0])) {
            list.add(t);
        }
        return list;
    }

    public void loadTransactions(List<Transaction> transactionList) {
        transactions.clear();
        for (Transaction t : transactionList) {
            transactions.offer(t);
        }
    }
}
