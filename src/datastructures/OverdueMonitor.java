package datastructures;

import model.Transaction;
import java.time.LocalDate;
import java.util.*;
import java.time.temporal.ChronoUnit;

public class OverdueMonitor {
    private final PriorityQueue<Transaction> overdueQueue;
    private static final double DAILY_FINE = 0.50;
    private static final int GRACE_PERIOD_DAYS = 14;

    public OverdueMonitor() {
        this.overdueQueue = new PriorityQueue<>(
            Comparator.comparing(Transaction::getDueDate)  // Using dueDate instead of returnDate
        );
    }

    public void addTransaction(Transaction t) {
        if (t.getStatus().equals("BORROWED")) {
            overdueQueue.add(t);
        }
    }

    public List<Transaction> checkOverdueBooks() {
        List<Transaction> overdue = new ArrayList<>();
        LocalDate now = LocalDate.now();
        
        while (!overdueQueue.isEmpty()) {
            Transaction t = overdueQueue.peek();
            
            // Stop if we reach future-due books
            if (!t.getDueDate().isBefore(now)) break;
            
            t = overdueQueue.poll();
            if (t.getStatus().equals("BORROWED")) {
                t.setStatus("OVERDUE");
                overdue.add(t);
            }
        }
        return overdue;
    }

    public double calculateFine(Transaction t) {
        if (!t.getStatus().equals("OVERDUE")) return 0;
        
        long daysOverdue = ChronoUnit.DAYS.between(
            t.getDueDate(), 
            LocalDate.now()
        );
        return Math.max(0, daysOverdue * DAILY_FINE);
    }

    public List<Transaction> getCriticalOverdues() {
        LocalDate criticalDate = LocalDate.now().minusDays(GRACE_PERIOD_DAYS);
        List<Transaction> critical = new ArrayList<>();
        
        for (Transaction t : overdueQueue) {
            if (t.getDueDate().isBefore(criticalDate)) {
                critical.add(t);
            }
        }
        return critical;
    }
}