package functionality;
import model.Borrower;
import model.Transaction;
import java.time.LocalDate;
import java.util.List;
import datastructures.PriorityQueue;

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
}
