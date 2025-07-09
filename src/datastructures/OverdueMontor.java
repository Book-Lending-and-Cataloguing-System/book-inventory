package datastructures;

import model.Transaction;
import java.time.LocalDate;
import java.util.PriorityQueue;

public class OverdueMonitor {
    private PriorityQueue<Transaction> overdueBooks;

    public OverdueMonitor() {
        overdueBooks = new PriorityQueue<>((t1, t2) -> t1.getReturnDate().compareTo(t2.getReturnDate()));
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getReturnDate().isBefore(LocalDate.now())) {
            overdueBooks.offer(transaction);
        }
    }

    // TODO: Implement checkOverdue() to flag books overdue by >14 days
    // TODO: Implement updateFines() to calculate fines for borrowers
    // TODO: Justify use of PriorityQueue (O(log n) insertion, O(1) min retrieval)
}