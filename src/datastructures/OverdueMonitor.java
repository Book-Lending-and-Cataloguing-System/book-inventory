package datastructures;

import model.Borrower;
import model.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.PriorityQueue;

public class OverdueMonitor {
    private PriorityQueue<Transaction> overdueBooks;

    public OverdueMonitor() {
        overdueBooks = new PriorityQueue<>(
            (t1, t2) -> t1.getBorrowDate().plusDays(14).compareTo(t2.getBorrowDate().plusDays(14))
        );
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getStatus().equals("borrowed")) {
            LocalDate dueDate = transaction.getBorrowDate().plusDays(14);
            if (dueDate.isBefore(LocalDate.now())) {
                overdueBooks.offer(transaction);
            }
        }

    }


    // TODO: Implement checkOverdue() to flag books overdue by >14 days
    public void checkOverdue(List<Transaction> transactions) {
        overdueBooks.clear();
        for (Transaction t : transactions) {
            if (t.getStatus().equals("borrowed")) {
                LocalDate dueDate = t.getBorrowDate().plusDays(14);
                if (dueDate.isBefore(LocalDate.now())) {
                    overdueBooks.offer(t);
                }
            }
        }
    }

    // TODO: Implement updateFines() to calculate fines for borrowers

    public void updateFines(List<Borrower> borrowers) {
        while (!overdueBooks.isEmpty()) {
            Transaction t = overdueBooks.poll();
            long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(t.getBorrowDate().plusDays(14), LocalDate.now());

            for (Borrower b : borrowers) {
                if (b.getIdNumber().equals(t.getBorrowerId())) {
                    b.addFine(overdueDays * 1.0); // GHS 1 per day
                    break;
                }
            }
        }
    }

    // TODO: Justify use of PriorityQueue (O(log n) insertion, O(1) min retrieval)
}