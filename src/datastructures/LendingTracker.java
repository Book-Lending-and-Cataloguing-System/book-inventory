package datastructures;

import model.Transaction;
import java.util.LinkedList;
import java.util.Queue;

public class LendingTracker {
    private Queue<Transaction> transactions;

    public LendingTracker() {
        transactions = new LinkedList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.offer(transaction);
    }

    // TODO: Implement returnBook() to update transaction status
    // TODO: Implement listTransactions() to display all transactions
    // TODO: Justify use of Queue (FIFO for borrowing order) vs. Stack (LIFO)
}